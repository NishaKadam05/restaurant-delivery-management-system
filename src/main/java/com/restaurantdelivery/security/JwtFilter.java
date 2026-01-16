package com.restaurantdelivery.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	private final JwtUtil jwtUtil;

	public JwtFilter(JwtUtil jwtUtil) {
		super();
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authHeader= request.getHeader("Authorization");
		
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		final String token = authHeader.substring(7);
		
		try {
			if( jwtUtil.isTokenValid(token)) {
				Claims claims = jwtUtil.extractAllClaims(token);
				String email= claims.getSubject();
				String role= claims.get("role", String.class);
				
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+ role);
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,
						null, Collections.singletonList(authority));
				
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			}
		} catch(Exception e) {
			SecurityContextHolder.clearContext();
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	

}
