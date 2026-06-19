package com.sri.jobportal.application.service;

import java.util.List;

import com.sri.jobportal.application.dto.ApplicationResponseDTO;

public interface ApplicationService {

	ApplicationResponseDTO applyForJob(Long jobId);
	
	List<ApplicationResponseDTO> getMyApplications();
	
}
