💳 ApiLucaBank

ApiLucaBank is a secure, scalable REST API built with Java 21 and Spring Boot, following clean code principles, layered architecture, and production-ready best practices.

The project focuses on security, maintainability, and extensibility, designed to evolve easily as new features are introduced.

🚀 Tech Stack

Java 21

Maven 4.0.3

Spring Boot

Spring Security

JWT Authentication

Spring Data JPA (Hibernate)

PostgreSQL

Bean Validation (@Valid)

Swagger / OpenAPI

Docker (planned)

Docker Compose (planned)

🏗 Architecture

The project follows a layered architecture with clear separation of concerns:

controller → service → repository → database
Layers

Controller → Handles HTTP requests and responses

Service → Business logic

Repository → Data access layer (JPA)

Security → JWT filter, authentication and authorization

Exception → Global error handling

DTO → Data transfer objects with validation

This approach ensures:

Clean and maintainable code

Scalability

Testability

Easy feature expansion

🔐 Security

Security is a core pillar of ApiLucaBank.

Implemented Features

JWT-based authentication

Role-based authorization

Authority-based permission system

Custom AuthenticationEntryPoint

Secure password handling

CORS configuration

Dev and Prod profiles

Roles

ROLE_USER

ROLE_ADMIN

ROLE_SUPERADMIN

Authorities (example)

USER_READ

USER_WRITE

ADMIN_READ

ADMIN_WRITE

This allows fine-grained access control and scalable permission management.

✅ DTO Validation

All request payloads are validated using Bean Validation with @Valid.

Example:

@Size(min = 2, max = 100)
@NotBlank
private String lastName;

Validation errors are automatically intercepted and returned in a standardized JSON format.

🛑 Standardized Error Handling

The API uses a global exception handler (@RestControllerAdvice) to ensure consistent responses.

All errors follow a structured JSON format:

{
  "timestamp": "2026-02-28T00:00:00Z",
  "status": 400,
  "error": "Validation Failed",
  "message": "Invalid input",
  "path": "/auth/register",
  "details": {
    "lastName": "Last name must be between 2 and 100 characters"
  }
}

Handled cases:

400 – Validation errors

401 – Authentication errors (invalid/expired JWT)

403 – Access denied

404 – Resource not found

409 – Data conflicts (email/passport already exists)

500 – Internal server error (sanitized response)

Stack traces are never exposed to the client.

⚙ Environment Configuration

The project supports environment-based configuration:

application-dev.yml

application-prod.yml

Configured features:

Database connection

JWT secret & expiration

CORS settings

Hibernate configuration

Connection pool configuration

Profile activation

🛢 Database

PostgreSQL

JPA / Hibernate

Unique constraints (email, passport)

Connection pool configured

Clean entity relationships

📄 API Documentation

Swagger is integrated for interactive documentation.

After starting the application:

http://localhost:8080/swagger-ui/index.html
🐳 Docker (Planned Implementation)

The project will be containerized using:

One container for PostgreSQL

One container for the Spring Boot application

Docker bridge network between containers

Port 8080 exposed for API access

Architecture:

PostgreSQL Container
        ↕
Bridge Network
        ↕
Spring Boot Container
        ↕
localhost:8080

This setup will provide:

Easy deployment

Environment isolation

Scalable infrastructure

Reproducible environments

🎯 Project Goals

Production-ready security

Clean architecture

Expandable structure

Modern backend standards

Containerized deployment

Maintainable codebase

📌 Future Improvements

Full Docker & Docker Compose setup

CI/CD pipeline

Integration and unit tests

Logging improvements

Monitoring with Spring Actuator

Rate limiting

👨‍💻 Author

Lucas Macedo
github: devlucas-java
instagram: lucas___sdk2
instagram: devlucas-java
Backend Developer focused on secure and scalable systems.