# Deployment Status Microservice

This document describes the implementation of a microservice for checking the deployment status of the core module.

## Overview

A new endpoint has been added to the `HealthCheckController` that provides information about the deployment status of the core module, including the current version of the deployed application.

## Endpoint Details

### GET /api/health/status

This endpoint returns the following information:

- `status`: The current status of the application (UP if the application is running)
- `applicationName`: The name of the application (apply-core)
- `version`: The current version of the application (0.0.1-SNAPSHOT)
- `deploymentTimestamp`: The timestamp when the status was checked (current time)

### Example Response

```json
{
  "status": "UP",
  "applicationName": "apply-core",
  "version": "0.0.1-SNAPSHOT",
  "deploymentTimestamp": "2025-06-02T23:45:30.123456"
}
```

## Implementation Details

The implementation consists of the following changes:

1. Added new properties to `HealthCheckController`:
   - `applicationVersion`: Injected from application.version property
   - `applicationName`: Injected from application.name property

2. Added a new endpoint method `getDeploymentStatus()` to `HealthCheckController` that returns the deployment status information.

3. Updated `application.properties` to include:
   - `application.name=apply-core`
   - `application.version=0.0.1-SNAPSHOT`

4. Updated `application-test.properties` with the same properties for testing.

## Usage

To check the deployment status of the core module, send a GET request to:

```
http://localhost:8080/api/health/status
```

This will return a JSON response with the deployment status information.

## Future Enhancements

Possible future enhancements to this microservice could include:

1. Adding more detailed deployment information (e.g., build timestamp, git commit hash)
2. Implementing health checks for dependent services
3. Adding authentication/authorization to the endpoint if needed
4. Integrating with monitoring systems for automated alerts