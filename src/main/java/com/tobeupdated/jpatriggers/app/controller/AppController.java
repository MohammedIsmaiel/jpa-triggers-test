package com.tobeupdated.jpatriggers.app.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class AppController {

    private final JobLauncher jobLauncher;
    private final Job importUserJob;

    @GetMapping("/launch")
    public String launchJob() throws JobExecutionAlreadyRunningException, JobRestartException,
            JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        long currentTime = System.currentTimeMillis();
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", currentTime)
                .toJobParameters();
        jobLauncher.run(importUserJob, jobParameters);
        return "done";
    }
}
