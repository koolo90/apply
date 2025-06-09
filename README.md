# Apply Backend

This is the backend application for the Apply project, developed by BroCode.

## Project Structure

The project is organized into multiple modules:

- **core**: Contains the main application logic, models, services, and repositories
- **apply-web**: Web frontend application
- **apply-mobile**: Mobile application
- **infra**: Infrastructure configuration

## Technology Stack

- Java
- Spring Boot
- Gradle (with Kotlin DSL)
- Angular (for web frontend)

## Getting Started

### Prerequisites

- JDK 11 or higher
- Gradle
- Docker (for running the infrastructure)

### Running the Application

1. Start the required infrastructure:
   ```
   docker-compose -f infra/src/compose/compose.dev.yml up -d
   ```

2. Run the application:
   ```
   ./gradlew bootRun
   ```

3. For the web frontend:
   ```
   cd apply-web
   npm install
   npm start
   ```

## Development

This project uses Gradle for build automation. You can use the following: commands:

- `./gradlew build`: Build the project
- `./gradlew test`: Run tests
- `./gradlew bootRun`: Run the application

Example of git expirience:
Octopus merge: https://github.com/koolo90/apply/commit/c983871ebf7ab43112a426bd80a07d86a75ae33a
   