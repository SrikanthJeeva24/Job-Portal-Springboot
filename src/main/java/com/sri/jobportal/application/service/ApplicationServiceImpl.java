package com.sri.jobportal.application.service;

import java.util.List;
import java.util.Objects;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sri.jobportal.application.dto.ApplicationResponseDTO;
import com.sri.jobportal.application.entity.ApplicationEntity;
import com.sri.jobportal.application.entity.ApplicationStatus;
import com.sri.jobportal.application.repository.ApplicationRepository;
import com.sri.jobportal.common.exception.DuplicateResourceException;
import com.sri.jobportal.common.exception.ResourceNotFoundException;
import com.sri.jobportal.job.entity.JobEntity;
import com.sri.jobportal.job.respository.JobRepository;
import com.sri.jobportal.user.entity.UserEntity;
import com.sri.jobportal.user.repository.UserRepository;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	private final ApplicationRepository applicationRepository;
	private final UserRepository userRepository;
	private final JobRepository jobRepository;

	public ApplicationServiceImpl(ApplicationRepository applicationRepository, UserRepository userRepository,
			JobRepository jobRepository) {
		this.applicationRepository = applicationRepository;
		this.userRepository = userRepository;
		this.jobRepository = jobRepository;
	}
 
	@Override
	public List<ApplicationResponseDTO> getMyApplications() {
		
		String email = Objects.requireNonNull(SecurityContextHolder
                .getContext().getAuthentication()).getName();
		
		UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		
	    List<ApplicationEntity> applications = applicationRepository.findByUser(user);
	    
	    return applications.stream()
	            .map(application -> new ApplicationResponseDTO(
	                    application.getId(),
	                    application.getUser().getEmail(),
	                    application.getJob().getTitle(),
	                    application.getStatus()
	            ))
	            .toList();
	}
	
	@Override
	public ApplicationResponseDTO applyForJob(
	        Long jobId) {

	    // Get currently logged-in user's email
	    // from JWT authentication context
	    String email =
	            Objects.requireNonNull(SecurityContextHolder
                                .getContext()
                                .getAuthentication())
	                    .getName();

		
	    // Fetch logged-in user from database
	    UserEntity user =
	            userRepository
	                    .findByEmail(email)
	                    .orElseThrow(() ->
	                            new ResourceNotFoundException(
	                                    "User Not Found"));

	    // Fetch requested job
	    JobEntity job =
	            jobRepository
	                    .findById(jobId)
	                    .orElseThrow(() ->
	                            new ResourceNotFoundException(
	                                    "Job Not Found"));

	    // Prevent duplicate applications
	    boolean isAlreadyApplied =
	            applicationRepository
	                    .existsByUserAndJob(
	                            user,
	                            job);

	    if (isAlreadyApplied) {
	        throw new DuplicateResourceException(
	                "Job Already Applied");
	    }

	    // Create new application
	    ApplicationEntity application =
	            new ApplicationEntity();

	    application.setUser(user);
	    application.setJob(job);

	    // Default status when applying
	    application.setStatus(
	            ApplicationStatus.APPLIED);

	    // Save application
	    ApplicationEntity saved =
	            applicationRepository
	                    .save(application);

	    // Return response DTO
	    return new ApplicationResponseDTO(
	            saved.getId(),
	            user.getEmail(),
	            job.getTitle(),
	            saved.getStatus());
	}
	
	

}
