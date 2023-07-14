package co.planez.padawan.auth;

import java.util.Optional;

import co.planez.padawan.domain.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class ApplicationAuthenticator implements Authenticator<String, User> {

	@Override
	public Optional<User> authenticate(String credentials) throws AuthenticationException {
		if (credentials == null || credentials.isEmpty()) {
			return Optional.empty();
		}
		
		Jws<Claims> claims = AuthUtils.instance().decodeClaims(credentials);
		User user = User.getByUsername(claims.getBody().getSubject());
    	if (user != null) {
    		return Optional.of(user);
    	}
		return Optional.empty();
	}
}