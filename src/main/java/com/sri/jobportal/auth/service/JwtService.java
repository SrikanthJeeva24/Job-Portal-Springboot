package com.sri.jobportal.auth.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
 
	private static final long JWT_EXPIRATION = 24 * 60 * 60 * 1000;
	
	@Value("${jwt_secret}")
	private String secretKey;
	
	public String generateToken(String email) {
		return Jwts.builder()
				   .subject(email)
				   .issuedAt(new Date())
				   .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
				   .signWith(
	                        Keys.hmacShaKeyFor(
	                                secretKey.getBytes()),
	                        Jwts.SIG.HS256)
	                .compact();
	}
	
    // parsing token  	
	private Claims extractAllClaims(String token) {
		
		return Jwts.parser()
				.verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	// Accessing Email from parse token
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}
	
    // Checking Token Exist or Not	
	public boolean validateToken(String token) {
		try {
			extractAllClaims(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}
	
}
