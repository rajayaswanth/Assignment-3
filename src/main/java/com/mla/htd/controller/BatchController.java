package com.mla.htd.controller;

import java.util.UUID;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {
	
	@Autowired
    JobLauncher jobLauncher;
      
    @Autowired
    Job job;
	
	@GetMapping("/")
	public void perform() throws Exception
    {
		JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", UUID.randomUUID().toString())
                .addLong("JobId",System.currentTimeMillis())
                .addLong("time",System.currentTimeMillis()).toJobParameters();
		
		JobExecution execution = jobLauncher.run(job, jobParameters);
		System.out.println("STATUS :: "+execution.getStatus());
    }

}
