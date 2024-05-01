package com.tobeupdated.jpatriggers.app.util;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.tobeupdated.jpatriggers.app.model.entity.User;
import com.tobeupdated.jpatriggers.app.repo.UserRepo;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
public class UseratchJob {
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private UserRepo userRepo;

    @Bean
    public Job importUserJob() {
        return new JobBuilder("importUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(importUserStep())
                .build();
    }

    @Bean
    public Step importUserStep() {
        return new StepBuilder("sampleStep", jobRepository)
                .<User, User>chunk(10, transactionManager)
                .reader(userReader())
                .writer(userWriter())
                .build();
    }

    @Bean
    public JsonItemReader<User> userReader() {
        return new JsonItemReaderBuilder<User>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(User.class))
                .resource(new ClassPathResource("data.json"))
                .name("userReader")
                .build();
    }

    ItemWriter<User> userWriter() {
        JpaItemWriter<User> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    // @Bean
    // public RepositoryItemWriter<User> userWriter() {
    // return new RepositoryItemWriterBuilder<User>()
    // .repository(userRepo)
    // .methodName("save")
    // .build();
    // }
    // @Bean
    // public FlatFileItemReader<User> userReader() {
    // FlatFileItemReader<User> reader = new FlatFileItemReader<>();
    // reader.setResource(new ClassPathResource("data.txt"));
    // DefaultLineMapper<User> lineMapper = new DefaultLineMapper<>();
    // // DelimitedLineTokenizer defaults to comma as its delimiter
    // lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
    // lineMapper.setFieldSetMapper(new UserFieldSetMapper());
    // reader.setLineMapper(lineMapper);
    // reader.open(new ExecutionContext());
    // return reader;
    // }

    // private static class UserFieldSetMapper implements FieldSetMapper<User> {
    // public User mapFieldSet(FieldSet fieldSet) {
    // User user = new User();
    // user.setUsername(fieldSet.readString(0));
    // return user;
    // }
    // }
}
