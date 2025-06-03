package com.brocode.apply.integration;

import com.brocode.apply.model.Users;
import com.brocode.apply.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test to verify that data is correctly inserted into the users table.
 * This test uses the H2 in-memory database with SQL initialization.
 * @DataJpaTest provides a standard configuration for JPA tests, including an embedded database,
 * a TestEntityManager, and Spring Data JPA repositories.
 */
@DataJpaTest
@ActiveProfiles("test") // Use test profile
public class UserDataIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
    public void testUsersDataInserted() {
        System.out.println("[DEBUG_LOG] Starting UserDataIntegrationTest.testUsersDataInserted");
        // Fetch all users from the database
        List<Users> users = (List<Users>) userRepository.findAll();
        System.out.println("[DEBUG_LOG] Found " + users.size() + " users in the database");

        // Verify that users were inserted
        assertFalse(users.isEmpty(), "Users table should not be empty");

        // Verify we have the expected number of users (5 from Flyway migration)
        assertEquals(5, users.size(), "Should have 5 users from Flyway migration");

        // Verify specific users exist
        assertTrue(users.stream().anyMatch(user -> "john@example.com".equals(user.getEmail())), 
                "John Doe should exist in the database");
        assertTrue(users.stream().anyMatch(user -> "jane@example.com".equals(user.getEmail())), 
                "Jane Smith should exist in the database");
        assertTrue(users.stream().anyMatch(user -> "bob@example.com".equals(user.getEmail())), 
                "Bob Johnson should exist in the database");
        assertTrue(users.stream().anyMatch(user -> "alice@example.com".equals(user.getEmail())), 
                "Alice Brown should exist in the database");
        assertTrue(users.stream().anyMatch(user -> "charlie@example.com".equals(user.getEmail())), 
                "Charlie Davis should exist in the database");

        // Verify user details for one user
        Users johnDoe = users.stream()
                .filter(user -> "john@example.com".equals(user.getEmail()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("John Doe not found"));

        assertEquals("John Doe", johnDoe.getName(), "Name should match");
    }
}
