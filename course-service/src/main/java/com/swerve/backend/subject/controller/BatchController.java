package com.swerve.backend.subject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.swerve.backend.subject.util.Utility.saveUploadedFile;

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



    @PreAuthorize("hasAnyAuthority('migrateCourses')")
    @PostMapping("/importcourses")
    public ResponseEntity<BatchStatus> importCsvToDBJob(@RequestParam("file") MultipartFile file) throws IOException, JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, NoSuchJobException {
        log.info("BatchController | importCourseCsvToDBJob is called");
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

            JobExecution jobExecution = jobLauncher.run(this.importCourseJob, jobParametersBuilder.toJobParameters());
            if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
                return new ResponseEntity<>( BatchStatus.COMPLETED,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(BatchStatus.ABANDONED,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            log.info("BatchController | importEnrollmentsCsvToDBJob | error : " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(BatchStatus.FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PreAuthorize("hasAnyAuthority('migrateEnrollments')")
    @PostMapping("/importenrollments")
    public ResponseEntity<BatchStatus> importEnrollmentsCsvToDBJob(@RequestParam("file") MultipartFile file) throws IOException, JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, NoSuchJobException {

        log.info("BatchController | importEnrollmentsCsvToDBJob is called");
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

            JobExecution jobExecution = jobLauncher.run(this.importEnrollmentsJob, jobParametersBuilder.toJobParameters());
            if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
                return new ResponseEntity<>( BatchStatus.COMPLETED,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(BatchStatus.ABANDONED,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            log.info("BatchController | importEnrollmentsCsvToDBJob | error : " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(BatchStatus.FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }

//        return new ResponseEntity<>("Batch Process Completed!!", HttpStatus.OK);
    }
}