package com.swerve.backend.subject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private final JobLauncher jobLauncher;
    private Job importEnrollmentsJob;
    private Job importCourseJob;
    @Autowired
    private JobLocator jobLocator;

    @Autowired
    public BatchController(JobLauncher jobLauncher,
                           @Qualifier("importEnrollmentsJob") Job importEnrollmentsJob,
                           @Qualifier("importCourseJob") Job importCourseJob) {
        this.jobLauncher = jobLauncher;
        this.importEnrollmentsJob = importEnrollmentsJob;
        this.importCourseJob = importCourseJob;
    }



    @PostMapping("/importcourses")
    public ResponseEntity<String> importCsvToDBJob() throws IOException, JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, NoSuchJobException {
//        this.importCourseJob=jobLocator.getJob("importCourseJob");
        log.info("BatchController | importCourseCsvToDBJob is called");

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(this.importCourseJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            log.info("BatchController | importEnrollmentsCsvToDBJob | error : " + e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>("Batch Process Completed!!", HttpStatus.OK);
    }
    @PostMapping("/importenrollments")
    public ResponseEntity<String> importEnrollmentsCsvToDBJob() throws IOException, JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, NoSuchJobException {

        log.info("BatchController | importEnrollmentsCsvToDBJob is called");

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(this.importEnrollmentsJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            log.info("BatchController | importEnrollmentsCsvToDBJob | error : " + e.getMessage());
            e.printStackTrace();
        }

        return new ResponseEntity<>("Batch Process Completed!!", HttpStatus.OK);
    }
}