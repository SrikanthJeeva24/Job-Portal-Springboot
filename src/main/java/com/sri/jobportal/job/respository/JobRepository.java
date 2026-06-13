package com.sri.jobportal.job.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sri.jobportal.job.entity.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, Long> {
 
	boolean existsByTitleAndCompanyNameAndLocation(String title, String companyName, String Location);
	
}
