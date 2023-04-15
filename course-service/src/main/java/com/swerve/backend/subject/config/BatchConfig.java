package com.swerve.backend.subject.config;

import com.swerve.backend.subject.dto.CourseDTO;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.repository.CourseRepository;
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
import com.swerve.backend.subject.config.JobCompletionNotificationListener;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private DataSource dataSource;

    @Value("${input}") Resource resource;


    @Bean
    public FlatFileItemReader<CourseDTO> reader(){
        FlatFileItemReader<CourseDTO> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new ClassPathResource("MOCK_DATA.csv"));
        itemReader.setName("courseItemReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }
    private LineMapper<CourseDTO> lineMapper() {
        DefaultLineMapper<CourseDTO> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(
                Course.getCourseFields()
        );

        BeanWrapperFieldSetMapper<CourseDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(CourseDTO.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
    @Bean
    public CourseProcessor processor() {
        return new CourseProcessor();
    }
    @Bean
    public JdbcBatchItemWriter<CourseImport> writer(){
        JdbcBatchItemWriter<CourseImport> jdbcBatchItemWriter=new JdbcBatchItemWriter<>();
        jdbcBatchItemWriter.setDataSource(dataSource);
        jdbcBatchItemWriter.setSql("insert into Course (course_code, credits, short_description, title,learning_track_id) " +
                "values(:course_code, :credits, :short_description, :title,:learning_track_id)");
        jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<CourseImport>());
        return jdbcBatchItemWriter;
    }

    @Bean
    public Step step1(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager, JdbcBatchItemWriter<CourseImport> writer) {
        return new StepBuilder("step1", jobRepository)
                .<CourseDTO, CourseImport> chunk(10, transactionManager)
                .reader(reader())
              .processor(processor())
                .writer(writer)
                .build();
    }
    @Bean
    public Job importCourseJob(JobRepository jobRepository,
                             JobCompletionNotificationListener listener, Step step1) {
        return new JobBuilder("importCourseJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }








}
