package com.sri.jobportal.user.dto;

import com.sri.jobportal.user.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
	private Long id;
	private String name;
	private String email;
	private Role role;
}
