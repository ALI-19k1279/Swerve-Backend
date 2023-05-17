package com.swerve.backend.userservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

    @PostMapping("/importlearners")
    public ResponseEntity<BatchStatus> importCsvToDBJob(@RequestParam("file") MultipartFile file) throws IOException, JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        log.info("BatchController | importStudentsCsvToDBJob is called");

        try {

            String fileName = file.getOriginalFilename();
            Path tempUploadsPath = Paths.get("tempuploads/");

            if (!Files.exists(tempUploadsPath)) {
                Files.createDirectories(tempUploadsPath);
            }
            Path fileToImport = tempUploadsPath.resolve(fileName);
            Files.copy(file.getInputStream(), fileToImport, StandardCopyOption.REPLACE_EXISTING);

            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            jobParametersBuilder.addLong("startAt", System.currentTimeMillis());
            jobParametersBuilder.addString("filePath", fileToImport.toAbsolutePath().toString());

            JobExecution jobExecution = jobLauncher.run(job, jobParametersBuilder.toJobParameters());
            if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
                return new ResponseEntity<>( BatchStatus.COMPLETED,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(BatchStatus.ABANDONED,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }  catch (JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            log.info("BatchController | importEnrollmentsCsvToDBJob | error : " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(BatchStatus.FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }

//        return new ResponseEntity<>("Batch Process Completed!!", HttpStatus.OK);
    }
}