package com.sri.jobportal.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Name Cannot be Empty!")
	private String name;
	
	@Column(unique = true, nullable = false)
	@NotBlank(message = "Email Cannot be Empty!")
    @Email(message = "Invalid Email Format")
	private String email;
	
    @Column(nullable = false)
    @NotBlank(message = "Password Cannot be Empty!")
	private String password;
    
    @Enumerated(EnumType.STRING)
    private Role role;
}
