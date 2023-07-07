package co.planez.padawan.auth;

import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.planez.padawan.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthUtils {
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(AuthUtils.class);

	private static AuthUtils instance = null;
	
	// For SecretKeySpec generation
	private String algorithm = "HmacSHA512";
	private byte[] encoded = {-8, -36, 93, 58, -106, 123, -77, -120, -119, 80, -67, -58, -103,
			                  40, 8, -81, 4, -91, 58, 83, -67, 69, 69, 71, 74, -109, -125, 67,
			                  -72, -39, -11, -64, 42, 1, 5, 17, -32, -97, -21, -67, -127, 47,
			                  -46, -108, 99, -69, 36, 120, -67, 92, 113, 21, 96, 34, 67, -12, -44,
			                  -31, -117, -37, 92, -97, -100, 67};

	private SecretKeySpec key;
	private JwtParser parser;
	
	public AuthUtils() {
		key = new SecretKeySpec(encoded, algorithm);
		parser = Jwts.parserBuilder().setSigningKey(key).build();
	}

	public static AuthUtils instance() {
		if (instance == null) {
			instance = new AuthUtils();
		}
		return instance;
	}
	
	
//	public SecretKeySpec getKey() {
//		return key;
//	}
//	
//	public JwtParser getParser() {
//		return parser;
//	}

	// The following header hack is due to (a) Chrome demanding SameSite be set
	// and (b) NewCookie having no way to freaking do that. WTF people? So instead
	// of using the .cookie() call on the Response the cookie gets turned into a
	// String, and the SameSite setting gets added to the end, and the .header()
	// function is used instead. What a hack.
//	public static String sameSite(NewCookie cookie) {
//		return cookie.toString() + ";SameSite=none";
//	}
//	
//	public Jws<Claims> decodeCookie(Cookie cookie) {
//		Jws<Claims> claims = null;
//		String jwt = cookie.getValue();
//		if (jwt != null && !jwt.isEmpty()) {
//			claims = decodeClaims(jwt);
//		}
//		return claims;
//	}

	public Jws<Claims> decodeClaims(String jwt) {
		Jws<Claims> claims = null;
		if (jwt != null && !jwt.isEmpty()) {
			claims = parser.parseClaimsJws(jwt);
		}
		return claims;
	}

	public String generateToken(User user) {
		// Now generate the Java Web Token
		// https://github.com/jwtk/jjwt
		// https://stormpath.com/blog/jwt-java-create-verify
		// https://scotch.io/tutorials/the-anatomy-of-a-json-web-token
		Claims claims = Jwts.claims();
		claims.setIssuedAt(new Date());
		claims.setSubject(user.getName());
		claims.put("fullname", user.getFullName());
		claims.put("userId", user.getId());
		
		String jwt = Jwts.builder().setClaims(claims).signWith(key, SignatureAlgorithm.HS512).compact();

		return jwt;
	}

//	public NewCookie generateCookie(User user) {
//		boolean secure = ShoppingConfiguration.instance().getMode().compareTo("DEV") == 0 ? false : true;
//		int maxAge = 86400*30;  // Seconds per day, times days to live
//		//NewCookie(String name, String value, String path, String domain, String comment, int maxAge, boolean secure, boolean httpOnly)
//		NewCookie cookie = new NewCookie("nibbles.token", generateToken(user), "/", null, "Nibbles Shopping ID", maxAge, secure, true);
//		return cookie;
//	}
//
//	public NewCookie removeCookie() {
//		boolean secure = ShoppingConfiguration.instance().getMode().compareTo("DEV") == 0 ? false : true;
//		return new NewCookie("nibbles.token", null, "/", null, "Nibbles Shopping ID", 0, secure, true);
//	}

//	public User getUserFromCookie(Cookie cookie) {
//		User user = null;
//		
//		if (cookie != null) {
//			Jws<Claims> claims = decodeCookie(cookie);
//			Claims body = claims.getBody();
//			String name = (String) body.getSubject();
//			user = User.getByUsername(name);
//		}
//		return user;
//	}
}
