package com.brocode.apply.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * A very simple test that doesn't use any custom configuration and doesn't rely on the database at all.
 * This test is used to isolate the issue and see if it's related to the database configuration or something else.
 */
@SpringBootTest
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = {
    DataSourceAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class
})
public class SimpleMockTest {

    @Test
    public void contextLoads() {
        System.out.println("[DEBUG_LOG] Application context loaded successfully without database");
    }
}