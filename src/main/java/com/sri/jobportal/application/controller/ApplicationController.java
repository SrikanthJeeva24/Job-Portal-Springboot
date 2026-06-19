package com.sri.jobportal.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sri.jobportal.application.dto.ApplicationResponseDTO;
import com.sri.jobportal.application.service.ApplicationService;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

	private final ApplicationService applicationService;
	
	public ApplicationController(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
    @PostMapping("/jobs/{jobId}")
    public ResponseEntity<ApplicationResponseDTO>
    applyToJob(
            @PathVariable Long jobId) {
        ApplicationResponseDTO res =
                applicationService
                        .applyForJob(jobId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(res);
    }
    
    @GetMapping
    public ResponseEntity<List<ApplicationResponseDTO>> getMyApplications() {
    	List<ApplicationResponseDTO> res = applicationService.getMyApplications();
    	return ResponseEntity.ok(res);
    }
    
}
