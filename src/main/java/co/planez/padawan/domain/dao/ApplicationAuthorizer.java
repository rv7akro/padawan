package co.planez.padawan.domain.dao;

import jakarta.ws.rs.container.ContainerRequestContext;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.planez.padawan.domain.User;
import io.dropwizard.auth.Authorizer;

public class ApplicationAuthorizer implements Authorizer<User> {
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(ApplicationAuthorizer.class);

    @Override
	public boolean authorize(User principal, String role, @Nullable ContainerRequestContext requestContext) {
    	Role userRole = Role.valueOf(role);
    	switch (userRole) {
    		case ADMIN : return principal.isAdmin(); 
    		case INSTRUCTOR : return principal.isInstructor();
    		case USER  : return true;
    		default : return false;
    	}
    }
}