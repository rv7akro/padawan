package co.planez.padawan.domain.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.planez.padawan.domain.User;
import io.dropwizard.auth.Authorizer;

public class ApplicationAuthorizer implements Authorizer<User> {
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(ApplicationAuthorizer.class);

    @Override
    public boolean authorize(User user, String role) {
    	Role userRole = Role.valueOf(role);
    	switch (userRole) {
    		case ADMIN : return user.isAdmin(); 
    		case INSTRUCTOR : return user.isInstructor();
    		case USER  : return true;
    		default : return false;
    	}
    }
}