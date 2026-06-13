package com.sri.jobportal.job.dto;


import java.time.LocalDateTime;

import lombok.Data;

@Data
public class JobResponseDTO {

	private Long id;
	
	private String title;
	
	private String description;
	
	private String location;
	
	private Double salary;
	
	private String companyName;
	
	private String skillsRequired;

	private LocalDateTime createdAt;
	
}
