package com.sri.jobportal.job.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sri.jobportal.job.entity.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, Long> {
 
	boolean existsByTitleAndCompanyNameAndLocation(String title, String companyName, String Location);
	
    List<JobEntity>
    findByTitleContainingIgnoreCase(
            String title);
}
