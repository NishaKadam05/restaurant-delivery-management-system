package com.restaurantdelivery.security;


import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {

	private String jwtSecret = "ThisIsASecretKeyThatIsAtLeast32CharsLong";
	private long jwtExpirationMs = 86400000;
	
	private SecretKey getSigningKey() {
		
		byte[] keyBytes = jwtSecret.getBytes();
		return Keys.hmacShaKeyFor(keyBytes);
		
	}
	
	public String generateToken(String email, String role) {
		
		return Jwts.builder()
				.setSubject(email)
				.addClaims(Map.of("role",role))
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+jwtExpirationMs ))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public boolean isTokenValid(String token) {
		try {
			extractAllClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
	
	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String extractEmail(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	public String extractRole(String token) {
		return extractAllClaims(token).get("role", String.class);
	}
	
	public Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}
}
