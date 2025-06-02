package com.brocode.apply.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test for the HealthCheckController.
 * Tests the deployment status endpoint.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class HealthCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${application.version:0.0.1-SNAPSHOT}")
    private String expectedVersion;

    @Value("${application.name:apply-core}")
    private String expectedName;

    @Test
    public void testDeploymentStatusEndpoint() throws Exception {
        System.out.println("[DEBUG_LOG] Starting HealthCheckControllerTest.testDeploymentStatusEndpoint");
        
        // Test the /api/health/status endpoint
        mockMvc.perform(get("/api/health/status"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.status", is("UP")))
                .andExpect(jsonPath("$.applicationName", is(expectedName)))
                .andExpect(jsonPath("$.version", is(expectedVersion)))
                .andExpect(jsonPath("$.deploymentTimestamp", notNullValue()));
        
        System.out.println("[DEBUG_LOG] Deployment status endpoint test passed");
    }

    @Test
    public void testDatabaseHealthEndpoint() throws Exception {
        System.out.println("[DEBUG_LOG] Starting HealthCheckControllerTest.testDatabaseHealthEndpoint");
        
        // Test the /api/health/database endpoint
        MvcResult result = mockMvc.perform(get("/api/health/database"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.status", is("UP")))
                .andExpect(jsonPath("$.message", containsString("Database connection is valid")))
                .andReturn();
        
        System.out.println("[DEBUG_LOG] Database health endpoint test passed");
        System.out.println("[DEBUG_LOG] Response: " + result.getResponse().getContentAsString());
    }
}