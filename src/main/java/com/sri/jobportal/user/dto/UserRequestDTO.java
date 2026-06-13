package com.sri.jobportal.user.dto;

import com.sri.jobportal.user.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequestDTO {

	@NotBlank(message = "Name cannot be Empty!")
	private String name;

	@NotBlank(message = "Email cannot be Empty!")
	@Email(message = "Invalid Email Format")
	@Pattern(
			regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
			message = "Email must contain valid domain"
	)
	private String email;

	@NotBlank(message = "Please Enter a Password")
	@Pattern(
			regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
			message = "Password must be at least 8 characters and contain letters and numbers"
	)
	private String password;

	@NotNull(message = "Role cannot be Empty!")
	private Role role;
}
