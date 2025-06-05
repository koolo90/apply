package com.brocode.apply.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * A very simple test that uses @WebMvcTest, which only loads the web layer of the application.
 * This test is used to isolate the issue and see if it's related to the web layer or something else.
 */
@WebMvcTest
@ActiveProfiles("test")
public class SimpleWebMvcTest {

    @Test
    public void contextLoads() {
        System.out.println("[DEBUG_LOG] Web application context loaded successfully");
    }
}