package com.sri.jobportal.user.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sri.jobportal.common.exception.DuplicateResourceException;
import com.sri.jobportal.common.exception.UserNotFoundException;
import com.sri.jobportal.user.dto.UserRequestDTO;
import com.sri.jobportal.user.dto.UserResponseDTO;
import com.sri.jobportal.user.entity.UserEntity;
import com.sri.jobportal.user.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public List<UserResponseDTO> getAllUsers() {
		return userRepository.findAll()
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	public UserResponseDTO getUserById(Long id) {
		UserEntity user = findUser(id);
		return mapToResponse(user);
	}

	public UserResponseDTO createUser(UserRequestDTO request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new DuplicateResourceException("User with that Email already Exists!!");
		}

		UserEntity user = new UserEntity();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(request.getRole());

		return mapToResponse(userRepository.save(user));
	}

	public UserResponseDTO updateUser(Long id, UserRequestDTO request) {
		UserEntity user = findUser(id);

		userRepository.findByEmail(request.getEmail())
				.filter(existingUser -> !existingUser.getId().equals(id))
				.ifPresent(existingUser -> {
					throw new DuplicateResourceException("User with that Email already Exists!!");
				});

		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(request.getRole());

		return mapToResponse(userRepository.save(user));
	}

	public void deleteUser(Long id) {
		UserEntity user = findUser(id);
		userRepository.delete(user);
	}

	private UserEntity findUser(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User Not Found"));
	}

	private UserResponseDTO mapToResponse(UserEntity user) {
		return new UserResponseDTO(
				user.getId(),
				user.getName(),
				user.getEmail(),
				user.getRole()
		);
	}
}
