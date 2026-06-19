package com.sri.jobportal.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sri.jobportal.application.entity.ApplicationEntity;
import com.sri.jobportal.job.entity.JobEntity;
import com.sri.jobportal.user.entity.UserEntity;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {
  
	boolean existsByUserAndJob(UserEntity user, JobEntity job);
	
	List<ApplicationEntity> findByUser(UserEntity user);
	
}
