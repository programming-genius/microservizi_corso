package it.app.mobile.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JWTAuthenticationProvider implements AuthenticationProvider {

	private static final String SECRETKEY = Base64.getEncoder().encodeToString("abc1234".getBytes());

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String password = ((UserDetails)authentication.getPrincipal()).getPassword();
		if (validateToken(password)) {
			return authentication;
		}
		throw new BadCredentialsException("Invalid token");
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
	}

	private boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token);
			if (claims.getBody().getExpiration().before(new Date())) {
				return false;
			}
			return true;
		}catch(Exception e) {
			//throw new IllegalArgumentException("Expired or invalid JWT token");
			e.printStackTrace();
			return false;
		}
	}
}
