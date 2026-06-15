package com.sri.jobportal.job.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sri.jobportal.job.dto.JobRequestDTO;
import com.sri.jobportal.job.dto.JobResponseDTO;
import com.sri.jobportal.job.service.JobService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

	private final JobService jobService;

	public JobController(JobService jobService) {
		this.jobService = jobService;
	}

	@GetMapping("/search")
	public ResponseEntity<List<JobResponseDTO>> searchJobs(@RequestParam String title) {
		return ResponseEntity.ok(jobService.searchJobs(title));
	}

	@GetMapping
	public ResponseEntity<List<JobResponseDTO>> getAllJobs() {
		return ResponseEntity.ok(jobService.getAllJobs());
	}

	@GetMapping("/{id}")
	public ResponseEntity<JobResponseDTO> getJobById(@PathVariable Long id) {
		return ResponseEntity.ok(jobService.getJobById(id));
	}

	@PostMapping
	public ResponseEntity<JobResponseDTO> createJob(@Valid @RequestBody JobRequestDTO req) {
		JobResponseDTO res = jobService.createJob(req);

		return ResponseEntity.status(HttpStatus.CREATED).body(res);
	}

	@PutMapping("/{id}")
	public ResponseEntity<JobResponseDTO> updateJob(@PathVariable Long id, @Valid @RequestBody JobRequestDTO req) {
		return ResponseEntity.ok(jobService.updateJob(id, req));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteJob(@PathVariable Long id) {

		jobService.deleteJob(id);

		return ResponseEntity.noContent().build();
	}
}
