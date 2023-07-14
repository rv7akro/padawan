package co.planez.padawan.resources;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import java.util.List;
import java.util.ArrayList;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonView;

import co.planez.padawan.PadawanConfiguration;
import co.planez.padawan.auth.AuthUtils;
import co.planez.padawan.auth.HashUtils;
import co.planez.padawan.domain.User;
import co.planez.padawan.domain.Role;
import co.planez.padawan.domain.SummaryView;

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
	
	@GET
	@PermitAll
	@JsonView(SummaryView.Normal.class)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@Context SecurityContext context) {
		User user = (User)context.getUserPrincipal();
		return Response.ok().entity(user).build();
	}
	
	@GET
	@Path("details")
	@RolesAllowed(Role.Names.USER)
	@JsonView(SummaryView.Normal.class)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserDetails(@Context SecurityContext context) {
		User user = (User)context.getUserPrincipal();
		return Response.ok().entity(user).build();
	}
	
	@POST
	@Path("login")
	@JsonView(SummaryView.Login.class)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(Map<String, String> request) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String email = request.get("email");
		String password = request.get("password");
		
		User user = User.getByUsername(email.trim());
		if (user != null) {
			String storedPassword = user.getPassword();
			if (storedPassword == null || !HashUtils.validatePassword(password, storedPassword)) {
				LOG.info("User '{}' bad password.", email);
				return Response.status(404).build();
			}
			String token = AuthUtils.instance().generateToken(user);
			user.setToken(token);
			LOG.info("User '{}' logged in.", user.getName());
			
			return Response.ok(user).build();
		} else {
			LOG.info("User '{}' not found.", email);
			return Response.status(404).build();
		}
	}
}
