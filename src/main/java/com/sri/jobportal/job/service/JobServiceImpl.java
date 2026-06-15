package com.sri.jobportal.job.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sri.jobportal.common.exception.DuplicateResourceException;
import com.sri.jobportal.common.exception.ResourceNotFoundException;
import com.sri.jobportal.job.dto.JobRequestDTO;
import com.sri.jobportal.job.dto.JobResponseDTO;
import com.sri.jobportal.job.entity.JobEntity;
import com.sri.jobportal.job.respository.JobRepository;

@Service
public class JobServiceImpl implements JobService {

	private final JobRepository jobRepository;
	
	public JobServiceImpl(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}
	
	@Override
	public JobResponseDTO createJob(
	        JobRequestDTO request) {

	    boolean isExist =
	            jobRepository
	                    .existsByTitleAndCompanyNameAndLocation(
	                            request.getTitle(),
	                            request.getCompanyName(),
	                            request.getLocation());

	    if (isExist) {
	        throw new DuplicateResourceException(
	                "Job Already Exists");
	    }

	    JobEntity job = new JobEntity();

	    job.setTitle(request.getTitle());
	    job.setDescription(request.getDescription());
	    job.setCompanyName(request.getCompanyName());
	    job.setLocation(request.getLocation());
	    job.setSalary(request.getSalary());
	    job.setSkillsRequired(request.getSkillsRequired());

	    JobEntity savedJob =
	            jobRepository.save(job);

	    JobResponseDTO response =
	            new JobResponseDTO();

	    response.setId(savedJob.getId());
	    response.setTitle(savedJob.getTitle());
	    response.setDescription(savedJob.getDescription());
	    response.setLocation(savedJob.getLocation());
	    response.setSalary(savedJob.getSalary());
	    response.setCompanyName(savedJob.getCompanyName());
	    response.setSkillsRequired(savedJob.getSkillsRequired());

	    return response;
	}

	@Override
	public List<JobResponseDTO> getAllJobs() {
		// TODO Auto-generated method stub
		
		List<JobEntity> jobs = jobRepository.findAll();
	    
		return jobs.stream().map(this::mapToResponse).toList();
	}

	@Override
	public JobResponseDTO getJobById(Long id) {
		JobEntity job = jobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job Not Found!!"));
		return mapToResponse(job);
	}

	@Override
	public JobResponseDTO updateJob(Long id, JobRequestDTO request) {
		
		  JobEntity job = jobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job Not Found!!"));
		  
		   job.setTitle(request.getTitle());
		    job.setDescription(request.getDescription());
		    job.setCompanyName(request.getCompanyName());
		    job.setLocation(request.getLocation());
		    job.setSalary(request.getSalary());
		    job.setSkillsRequired(request.getSkillsRequired());
		    
		    JobEntity savedJob =
		            jobRepository.save(job);
		    
		  
		    JobResponseDTO response =
		            new JobResponseDTO();

		    response.setId(savedJob.getId());
		    response.setTitle(savedJob.getTitle());
		    response.setDescription(savedJob.getDescription());
		    response.setLocation(savedJob.getLocation());
		    response.setSalary(savedJob.getSalary());
		    response.setCompanyName(savedJob.getCompanyName());
		    response.setSkillsRequired(savedJob.getSkillsRequired());

		    return response;
	}

	@Override
	public void deleteJob(Long id) {
		JobEntity job = jobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job Not Found!!"));
		
		jobRepository.delete(job);
		
	}
	
	@Override
	public List<JobResponseDTO> searchJobs(
	        String title) {

	    List<JobEntity> jobs =
	            jobRepository
	                    .findByTitleContainingIgnoreCase(
	                            title);

	    return jobs.stream()
	            .map(this::mapToResponse)
	            .toList();
	}
  	
	private JobResponseDTO mapToResponse(JobEntity job) {
		   JobResponseDTO response =
		            new JobResponseDTO();

		    response.setId(job.getId());
		    response.setTitle(job.getTitle());
		    response.setDescription(job.getDescription());
		    response.setLocation(job.getLocation());
		    response.setSalary(job.getSalary());
		    response.setCompanyName(job.getCompanyName());
		    response.setSkillsRequired(job.getSkillsRequired());

		    return response;
	}
	
}
