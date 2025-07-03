package com.brocode.apply;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.kafka.KafkaItemWriter;
import org.springframework.batch.item.kafka.builder.KafkaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class BatchConfiguration {
    @Bean public FlatFileItemReader<Person> flatFileItemReader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names("firstName", "lastName", "age")
                .targetType(Person.class)
                .build();
    }

    @Bean public PersonRowMapper personRowMapper() {
        return new PersonRowMapper();
    }

    @Bean public JdbcCursorItemReader<Person> databaseCursorItemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Person>()
                .name("personItemReader")
                .dataSource(dataSource)
                .sql("SELECT first_name, last_name, age FROM people")
                .rowMapper(personRowMapper())
                .maxRows(10)
                .fetchSize(10)
                .queryTimeout(10_000)
                .build();
    }

    @Bean  PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()
                .sql("INSERT INTO people (first_name, last_name, age) VALUES (:firstName, :lastName, :age)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<Person, Person> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<Person, Person> kafkaTemplate() {
        KafkaTemplate<Person, Person> personPersonKafkaTemplate = new KafkaTemplate<>(producerFactory());
        personPersonKafkaTemplate.setDefaultTopic("test-topic");
        return personPersonKafkaTemplate;
    }

    @Bean KafkaItemWriter<Person, Person> kafkaItemWriter(KafkaTemplate<Person, Person> kafkaTemplate) {
        return new KafkaItemWriterBuilder<Person, Person>()
                .kafkaTemplate(kafkaTemplate)
                .itemKeyMapper(new PersonConverter())
                .build();
    }

    @Bean public Job importUserJob(JobRepository jobRepository, JobCompletionNotificationListener listener,
                                   Step importData, Step sendToKafka) {
        return new JobBuilder("importUserJob", jobRepository)
                .listener(listener)
                .start(importData)
                .start(sendToKafka)
                .build();
    }

    @Bean public Step importData(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
                           FlatFileItemReader<Person> reader, PersonItemProcessor processor,
                                 JdbcBatchItemWriter<Person> writer) {
        return new StepBuilder("Step 1 - Import data", jobRepository)
                .<Person, Person>chunk(3, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean Step sendToKafka(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
                           JdbcCursorItemReader<Person> reader, PersonSender processor,
                           KafkaItemWriter<Person, Person> writer) {
        return new StepBuilder("Step 2 - Send to broker", jobRepository)
                .<Person, Person>chunk(3, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
