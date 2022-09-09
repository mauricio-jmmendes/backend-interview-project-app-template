package com.ninjaone.backendinterviewproject.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

	private static final Logger log = LoggerFactory.getLogger(JwtTokenService.class);
	private static final Long EXPIRATION_IN_MS = (long) (3600000 * 30);
	private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public String generateToken(Authentication authentication) {

		AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + EXPIRATION_IN_MS);

		Map<String, Object> claims = new HashMap<>();
		claims.put("id", authenticatedUser.getId());
		claims.put("username", authenticatedUser.getUsername());
		claims.put("email", authenticatedUser.getEmail());

		return Jwts.builder()
							 .setId(authenticatedUser.getId().toString())
							 .setSubject(authenticatedUser.getUsername())
							 .setClaims(claims)
							 .setIssuedAt(now)
							 .setExpiration(expiryDate)
							 .signWith(key)
							 .compact();
	}

	public Long getId(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		return Long.valueOf(claims.get("id").toString());
	}

	public String getEmail(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		return claims.get("email").toString();
	}

	public String getName(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		return claims.get("name").toString();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (SignatureException ex) {
			log.error("Invalid Jwt Signature : {}", ex.getMessage());
		} catch (MalformedJwtException | ExpiredJwtException ex) {
			log.error("Invalid Jwt Token : {}", ex.getMessage());
		} catch (UnsupportedJwtException | IllegalArgumentException ex) {
			log.error("Unsupported Jwt Token : {}", ex.getMessage());
		}
		return false;
	}
}
