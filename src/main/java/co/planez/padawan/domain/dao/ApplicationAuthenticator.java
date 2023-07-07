package co.planez.padawan.domain.dao;

import java.util.Optional;

import co.planez.padawan.auth.AuthUtils;
import co.planez.padawan.domain.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class ApplicationAuthenticator implements Authenticator<String, User> {

	@Override
	public Optional<User> authenticate(String credentials) throws AuthenticationException {
		Jws<Claims> claims = AuthUtils.instance().decodeClaims(credentials);
		User user = User.getByUsername(claims.getBody().getSubject());
    	if (user != null) {
    		return Optional.of(user);
    	}
		return Optional.empty();
	}
}