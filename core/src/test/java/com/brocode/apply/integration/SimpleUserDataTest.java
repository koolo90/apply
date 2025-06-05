package com.brocode.apply.integration;

import com.brocode.apply.model.Users;
import com.brocode.apply.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * A very simple test that uses @DataJpaTest and doesn't rely on any custom configuration.
 * This test is used to isolate the issue and see if it's related to the test itself or the configuration.
 */
@DataJpaTest
@ActiveProfiles("test")
@Sql({"/schema.sql", "/data.sql"}) // Initialize database with schema and data
public class SimpleUserDataTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testUserRepositoryWorks() {
        System.out.println("[DEBUG_LOG] Starting SimpleUserDataTest.testUserRepositoryWorks");

        // Fetch all users from the database
        List<Users> users = (List<Users>) userRepository.findAll();
        System.out.println("[DEBUG_LOG] Found " + users.size() + " users in the database");

        // Verify that users were inserted
        assertFalse(users.isEmpty(), "Users table should not be empty");
    }
}
