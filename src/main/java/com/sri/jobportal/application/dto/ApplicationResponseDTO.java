package com.sri.jobportal.application.dto;

import com.sri.jobportal.application.entity.ApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponseDTO {
 
	private Long id;
	private String applicantEmail;
	private String jobTitle;
	private ApplicationStatus applicationStatus;
	
}
