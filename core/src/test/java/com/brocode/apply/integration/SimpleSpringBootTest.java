package com.brocode.apply.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

/**
 * A very simple test that uses @SpringBootTest with a specific configuration class.
 * This test is used to isolate the issue and see if it's related to the test itself or the configuration.
 */
@SpringBootTest
@ActiveProfiles("test")
@Import(SimpleSpringBootTest.TestDataSourceConfig.class)
public class SimpleSpringBootTest {

    @Test
    public void contextLoads() {
        System.out.println("[DEBUG_LOG] Application context loaded successfully");
    }

    /**
     * Test configuration class that defines a minimal set of beans needed for the test.
     */
    @TestConfiguration
    public static class TestDataSourceConfig {

        @Bean
        @Primary
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .addScript("classpath:schema.sql")
                    .addScript("classpath:data.sql")
                    .build();
        }
    }
}