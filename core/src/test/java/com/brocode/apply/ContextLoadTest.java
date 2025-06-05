package com.brocode.apply;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Simple test to verify that the application context loads correctly.
 */
@SpringBootTest
@ActiveProfiles("test")
public class ContextLoadTest {

    @Test
    public void contextLoads() {
        // This test will pass if the application context loads successfully
        System.out.println("[DEBUG_LOG] Application context loaded successfully");
    }
}