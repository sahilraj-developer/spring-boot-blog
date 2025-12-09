package com.blog.blog.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HealthController provides a basic health-check endpoint
 * used to verify that the Spring Boot application is running.
 *
 * This is commonly used for:
 * - Deployment readiness checks
 * - Monitoring and uptime systems (Kubernetes, AWS Load Balancer, Docker health checks)
 * - CI/CD automated tests
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    /**
     * Health check endpoint.
     *
     * Endpoint: GET /api/health
     *
     * Example Response:
     * {
     *   "status": "UP",
     *   "service": "Spring Boot Blog Application",
     *   "timestamp": 1739027563981
     * }
     *
     * @return A map with system status and timestamp
     */
    @GetMapping("/health")
    public Map<String, Object> healthCheck() {

        Map<String, Object> response = new HashMap<>();

        // Indicating that service is running successfully
        response.put("status", "UP");

        // Custom application label
        response.put("service", "Spring Boot Blog Application");

        // Send Server time for debugging and monitoring
        response.put("timestamp", System.currentTimeMillis());

        return response;
    }
}
