package com.sri.jobportal.job.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobRequestDTO {

	@NotBlank(message = "Title Cannot be Empty")
	private String title;
	
	@NotBlank(message = "Description Cannot be Empty")
	private String description;
	
	@NotBlank(message = "Location Cannot be Empty")
	private String location;
	
	@NotNull(message = "Salary Cannot be Empty")
	private Double salary;
	
	@NotBlank(message = "Company Name Cannot be Empty")
	private String companyName;
	
	@NotBlank(message = "Skills Required Cannot be Empty")
	private String skillsRequired;
}
