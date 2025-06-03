package com.brocode.apply.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A very simple test that uses @JdbcTest, which only loads the JDBC-related components.
 * This test is used to isolate the issue and see if it's related to the JDBC configuration or something else.
 */
@JdbcTest
@ActiveProfiles("test")
public class SimpleJdbcTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    public void setup() {
        try {
            // Drop existing tables if they exist
            jdbcTemplate.execute("DROP TABLE IF EXISTS user_roles");
            jdbcTemplate.execute("DROP TABLE IF EXISTS roles");
            jdbcTemplate.execute("DROP TABLE IF EXISTS users");

            System.out.println("[DEBUG_LOG] Dropped existing tables");

            // Initialize the database with schema.sql and data.sql
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(new ClassPathResource("schema.sql"));
            populator.addScript(new ClassPathResource("data.sql"));
            populator.execute(dataSource);

            System.out.println("[DEBUG_LOG] Database initialized with schema.sql and data.sql");
        } catch (Exception e) {
            System.out.println("[DEBUG_LOG] Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testJdbcTemplate() {
        System.out.println("[DEBUG_LOG] Starting SimpleJdbcTest.testJdbcTemplate");

        // Query the database to count the number of users
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
        System.out.println("[DEBUG_LOG] Found " + count + " users in the database");

        // Verify that users were inserted
        assertEquals(5, count, "Should have 5 users in the database");
    }
}
