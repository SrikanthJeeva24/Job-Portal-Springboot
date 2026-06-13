package com.sri.jobportal.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sri.jobportal.auth.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final CustomUserDetailsService userDetailsService;

	public JwtAuthFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Read Authorization header
		String authHeader = request.getHeader("Authorization");

		// If header is missing or not Bearer token
		// skip JWT processing
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {

			filterChain.doFilter(request, response);
			return;
		}

		// Remove "Bearer "
		String jwt = authHeader.substring(7);

		// Extract email from token
		String email = jwtService.extractUsername(jwt);

		// Continue only if:
		// 1. Email exists
		// 2. User not already authenticated
		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			// Load user from database
			UserDetails userDetails = userDetailsService.loadUserByUsername(email);

			// Validate JWT
			if (jwtService.validateToken(jwt)) {

				// Create Spring Security auth object
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());

				// Attach request details
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// Tell Spring user is authenticated
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		// Continue request
		filterChain.doFilter(request, response);
	}
}
