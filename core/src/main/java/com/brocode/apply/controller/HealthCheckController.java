package com.brocode.apply.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthCheckController {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    @Value("${application.version:0.0.1-SNAPSHOT}")
    private String applicationVersion;

    @Value("${application.name:apply-core}")
    private String applicationName;

    @Autowired
    public HealthCheckController(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getDeploymentStatus() {
        Map<String, Object> response = new HashMap<>();

        // Application is running, so status is UP
        response.put("status", "UP");
        response.put("applicationName", applicationName);
        response.put("version", applicationVersion);
        response.put("deploymentTimestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/database")
    public ResponseEntity<Map<String, Object>> checkDatabaseConnection() {
        Map<String, Object> response = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {
            boolean isValid = connection.isValid(5); // 5 seconds timeout

            if (isValid) {
                response.put("status", "UP");
                response.put("message", "Database connection is valid");

                // Get database metadata
                response.put("database", connection.getMetaData().getDatabaseProductName());
                response.put("version", connection.getMetaData().getDatabaseProductVersion());

                // Test a simple query
                Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
                response.put("queryResult", result);

                return ResponseEntity.ok(response);
            } else {
                response.put("status", "DOWN");
                response.put("message", "Database connection is invalid");
                return ResponseEntity.status(503).body(response);
            }
        } catch (SQLException e) {
            response.put("status", "DOWN");
            response.put("message", "Failed to connect to database: " + e.getMessage());
            return ResponseEntity.status(503).body(response);
        }
    }
}
