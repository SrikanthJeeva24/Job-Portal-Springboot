package com.sri.jobportal.job.service;

import java.util.List;

import com.sri.jobportal.job.dto.JobRequestDTO;
import com.sri.jobportal.job.dto.JobResponseDTO;

public interface JobService {
 
	JobResponseDTO createJob(JobRequestDTO request);
	
	List<JobResponseDTO> getAllJobs();
	
	JobResponseDTO getJobById(Long id);
	
	JobResponseDTO updateJob(Long id, JobRequestDTO request);
	
	void deleteJob(Long id);
}
