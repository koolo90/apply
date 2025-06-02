package com.brocode.apply.integration;

import com.brocode.apply.model.Users;
import com.brocode.apply.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test to verify that data is correctly inserted into the users table.
 * This test uses the H2 in-memory database with Flyway migrations.
 */
@SpringBootTest
@ActiveProfiles("test") // Use test profile
public class UserDataIntegrationTest {

    @Autowired
    private UserRepository userRepository;

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
