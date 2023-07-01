package co.planez.padawan.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.planez.padawan.PadawanConfiguration;

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
	@Produces(MediaType.APPLICATION_JSON)
	public Response stats(@CookieParam("padawan.token") Cookie cookie) {
		return Response.ok().build();
	}

}
