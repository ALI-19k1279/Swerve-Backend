package com.swerve.backend.userservice.config;

import com.swerve.backend.userservice.dto.StudentImportDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import com.swerve.backend.userservice.config.JobCompletionNotificationListener;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private DataSource dataSource;


    @Bean
    public FlatFileItemReader<StudentImportDTO> reader(){
        FlatFileItemReader<StudentImportDTO> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new ClassPathResource("MOCK_DATA_std.csv"));
        itemReader.setName("stdItemReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }
    private LineMapper<StudentImportDTO> lineMapper() {
        DefaultLineMapper<StudentImportDTO> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(
                StudentImportDTO.getStudentFields()
        );

        BeanWrapperFieldSetMapper<StudentImportDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(StudentImportDTO.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
    @Bean
    public StudentProcessor processor() {
        return new StudentProcessor();
    }
    @Bean
    public JdbcBatchItemWriter<StudentImport> writer(){
        JdbcBatchItemWriter<StudentImport> jdbcBatchItemWriter=new JdbcBatchItemWriter<>();
        jdbcBatchItemWriter.setDataSource(dataSource);
        jdbcBatchItemWriter.setSql("insert into student (first_name, s_index, last_name, user_id,year_of_enrollment) " +
                "values(:first_name, :s_index, :last_name, :user_id,:year_of_enrollment)");
        jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<StudentImport>());
        return jdbcBatchItemWriter;
    }

    @Bean
    public Step step1(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager, JdbcBatchItemWriter<StudentImport> writer) {
        return new StepBuilder("step1", jobRepository)
                .<StudentImportDTO, StudentImport> chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
    @Bean
    public Job importCourseJob(JobRepository jobRepository,
                               JobCompletionNotificationListener listener, Step step1) {
        return new JobBuilder("importStudentJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

}
