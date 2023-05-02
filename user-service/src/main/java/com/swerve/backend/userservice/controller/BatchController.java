package com.swerve.backend.userservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path = "/batch")// Root path
@RequiredArgsConstructor
@Slf4j
public class BatchController {

    JobLauncher jobLauncher;
    Job job;

    @Autowired
    public BatchController(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @PostMapping("/importstudents")
    public ResponseEntity<String> importCsvToDBJob() throws IOException, JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        log.info("BatchController | importStudentsCsvToDBJob is called");

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            log.info("BatchController | importStudentsCsvToDBJob | error : " + e.getMessage());
            e.printStackTrace();
        }

        return new ResponseEntity<>("Batch Process Completed!!", HttpStatus.OK);
    }
}