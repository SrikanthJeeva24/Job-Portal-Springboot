package com.sri.jobportal.auth.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sri.jobportal.auth.dto.AuthResponseDTO;
import com.sri.jobportal.auth.dto.LoginRequestDTO;
import com.sri.jobportal.auth.dto.RegisterRequestDTO;
import com.sri.jobportal.common.exception.DuplicateResourceException;
import com.sri.jobportal.common.exception.UnAuthorizedException;
import com.sri.jobportal.common.exception.UserNotFoundException;
import com.sri.jobportal.user.entity.Role;
import com.sri.jobportal.user.entity.UserEntity;
import com.sri.jobportal.user.repository.UserRepository;

@Service
public class AuthService {

	private final UserRepository userRepository;	
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	public AuthService(
			UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}
	

	public AuthResponseDTO registerUser(RegisterRequestDTO request) {
		
		boolean isExists = userRepository.existsByEmail(request.getEmail());
		
		if(isExists) {
            throw new DuplicateResourceException("User with that Email already Exists!!");	
		}
		
		UserEntity user = new UserEntity();
		
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(Role.JOB_SEEKER);
		
		userRepository.save(user);
		
		return new AuthResponseDTO(
                "User Registered Successfully", null);
	}
	
	public AuthResponseDTO loginUser(LoginRequestDTO request) {
		
		// Checking User Exists
	   	UserEntity user =
    	        userRepository.findByEmail(request.getEmail())
    	        .orElseThrow(() ->
    	                new UserNotFoundException(
    	                        "User Not Found"));
        // Comparing password	   	
	   	boolean isValid = passwordEncoder.matches(request.getPassword(), user.getPassword());
	   	
	   	if(!isValid) {
	   		throw new UnAuthorizedException("Invalid Credentials");
	   	}
	   	
           // need to pass token in here	
	   	String token = jwtService.generateToken(request.getEmail());
	   	
	   	return new AuthResponseDTO("User LoggedIn Succesfully", token);
	}
	
}
