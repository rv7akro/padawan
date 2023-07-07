package co.planez.padawan.resources;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.planez.padawan.PadawanConfiguration;
import co.planez.padawan.domain.User;

/**
 * @author dwight
 *
 */
@Path("/")	// Note that this is actually accessed as /api due to the setUrPattern() call in PadawanService
public class PadawanResource {
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(PadawanResource.class);
	@SuppressWarnings("unused")
	private static PadawanConfiguration config;

	public PadawanResource(PadawanConfiguration config) throws IOException {
		PadawanResource.config = config;
	}
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response all(@CookieParam("padawan.token") Cookie cookie) {
		LOG.info("User count : {}", User.count());
		List<User> users = User.getAll();
		return Response.ok(users).build();
	}
	
	@GET
	@Path("make")
	@Produces(MediaType.APPLICATION_JSON)
	public Response stats(@CookieParam("padawan.token") Cookie cookie) {
		User user = new User("dfrye@planez.co", "Dwight Frye", null);
		user.setPassword("I am a nitwit");
		user.save();
		
		return Response.ok().build();
	}
	
	@GET
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@CookieParam("padawan.token") Cookie cookie) {
		User user = User.getByUsername("dfrye@planez.co");
		return Response.ok(user).build();
	}

	@GET
	@Path("get/id/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@CookieParam("padawan.token") Cookie cookie,
			@PathParam("userId") Long userId) {
		User user = User.getByID(userId);
		return Response.ok(user).build();
	}
	

	@GET
	@Path("get/name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserByName(@CookieParam("padawan.token") Cookie cookie,
			@PathParam("name") String name) {
		User user = User.getByUsername(name);
		return Response.ok(user).build();
	}


	@GET
	@Path("drop")
	@Produces(MediaType.APPLICATION_JSON)
	public Response dropUser(@CookieParam("padawan.token") Cookie cookie) {
		User.drop();
		return Response.ok().build();
	}
}
