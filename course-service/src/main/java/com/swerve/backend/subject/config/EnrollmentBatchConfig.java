package com.swerve.backend.subject.config;

import com.swerve.backend.subject.dto.SPGOC_DTO;
import com.swerve.backend.subject.dto.SPGOC_Import;
import com.swerve.backend.subject.model.Course;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class EnrollmentBatchConfig {
    @Autowired
    private DataSource dataSource;

    @Value("${input}") Resource resource;


    @Bean
    public FlatFileItemReader<SPGOC_DTO> enrollmentsReader(){
        FlatFileItemReader<SPGOC_DTO> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new ClassPathResource("MOCK_DATA_Enroll.csv"));
        itemReader.setName("EnrollmentItemReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }
    private LineMapper<SPGOC_DTO> lineMapper() {
        DefaultLineMapper<SPGOC_DTO> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(
                SPGOC_DTO.getEnrollmentFields()
        );

        BeanWrapperFieldSetMapper<SPGOC_DTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(SPGOC_DTO.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
    @Bean
    public SPGOC_Processor enrollmentProcessor() {
        return new SPGOC_Processor();
    }
    @Bean
    public JdbcBatchItemWriter<SPGOC_Import> enrollmentWriter(){
        JdbcBatchItemWriter<SPGOC_Import> jdbcBatchItemWriter=new JdbcBatchItemWriter<>();
        jdbcBatchItemWriter.setDataSource(dataSource);
        jdbcBatchItemWriter.setSql("INSERT INTO students_per_group_offered_course(" +
                "student_id, is_enrolled, group_id, offered_course_id)" +
                "VALUES (:student_id,:is_enrolled ,:group_id,:offered_course_id)");
        jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<SPGOC_Import>());
        return jdbcBatchItemWriter;
    }

    @Bean
    public Step enrollemntStep1(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager, JdbcBatchItemWriter<SPGOC_Import> enrollmentWriter) {
        return new StepBuilder("enrollemntStep1", jobRepository)
                .<SPGOC_DTO, SPGOC_Import> chunk(10, transactionManager)
                .reader(enrollmentsReader())
                .processor(enrollmentProcessor())
                .writer(enrollmentWriter)
                .build();
    }
    @Bean
    @Qualifier("importEnrollmentsJob")
    public Job importEnrollmentsJob(JobRepository jobRepository,
                               JobCompletionNotificationListener listener, Step enrollemntStep1) {
        return new JobBuilder("importEnrollmentsJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(enrollemntStep1)
                .end()
                .build();
    }








}
