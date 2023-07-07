package co.planez.padawan.resources;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.planez.padawan.PadawanConfiguration;
import co.planez.padawan.auth.AuthUtils;
import co.planez.padawan.auth.HashUtils;
import co.planez.padawan.domain.User;
import co.planez.padawan.domain.dao.Role;

/**
 * @author dwight
 *
 */
@Path("/user")	// Note that this is actually accessed as /api/user due to the setUrPattern() call in PadawanService
public class UserResource {
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);
	@SuppressWarnings("unused")
	private static PadawanConfiguration config;

	public UserResource(PadawanConfiguration config) throws IOException {
		UserResource.config = config;
	}
	
	@GET
	@Path("createUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUsers() throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(Role.ADMIN);
		roles.add(Role.USER);
		roles.add(Role.INSTRUCTOR);
		User user = new User("dfrye@planez.co", "Dwight Frye", roles);
		user.setPassword(HashUtils.generateStrongPasswordHash("padawan123"));
		user.save();
		
		return Response.ok().build();
	}
	
	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(Map<String, String> request) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String name = request.get("name");
		String password = request.get("password");
		
		User user = User.getByUsername(name.trim());
		if (user != null) {
			String storedPassword = user.getPassword();
			if (storedPassword == null || !HashUtils.validatePassword(password, storedPassword)) {
				return Response.status(404).build();
			}
			String token = AuthUtils.instance().generateToken(user);
			user.setToken(token);
			LOG.info("User '{}' logged in.", user.getName());
			
			return Response.ok(user).build();
		} else {
			LOG.info("User '{}' not found.", name);
			return Response.status(404).build();
		}
	}
}
