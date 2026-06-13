package com.sri.jobportal.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sri.jobportal.auth.dto.AuthResponseDTO;
import com.sri.jobportal.auth.dto.LoginRequestDTO;
import com.sri.jobportal.auth.dto.RegisterRequestDTO;
import com.sri.jobportal.auth.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponseDTO> registerUser(@Valid @RequestBody RegisterRequestDTO request) {
		return ResponseEntity.ok(authService.registerUser(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> loginUser(@Valid @RequestBody LoginRequestDTO request) {
		return ResponseEntity.ok(authService.loginUser(request));
	}
}
