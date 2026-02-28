# 💳 ApiLucaBank

Secure • Scalable • Production-Ready REST API

ApiLucaBank is a secure and extensible banking API built with Java 21 and Spring Boot, following clean architecture principles, layered design, and modern backend best practices.

The project is designed with a strong focus on:

🔐 Security-first architecture

🧱 Clean and maintainable code

📈 Scalability and extensibility

🏭 Production-grade standards

# 🚀 Tech Stack
Technology	Description
Java 21	Latest LTS version
Maven 4.0.3	Dependency management
Spring Boot	Application framework
Spring Security	Authentication & Authorization
JWT	Stateless authentication
Spring Data JPA (Hibernate)	ORM
PostgreSQL	Relational database
Bean Validation (@Valid)	DTO validation
Swagger / OpenAPI	API documentation
Docker (Planned)	Containerization
Docker Compose (Planned)	Multi-container setup
# 🏗 Architecture

The project follows a layered architecture with strict separation of concerns:

Controller → Service → Repository → Database
📂 Layers Overview
🔹 Controller

Handles HTTP requests and responses.

🔹 Service

Contains business logic and domain rules.

🔹 Repository

Data access layer using Spring Data JPA.

🔹 Security

JWT filter, authentication flow, authorization rules.

🔹 Exception

Centralized global error handling.

🔹 DTO

Validated request and response objects.

✅ Why This Architecture?

Clean and readable codebase

High cohesion & low coupling

Easy to test

Easy to expand

Production-oriented structure

# 🔐 Security

Security is the core pillar of ApiLucaBank.

✔ Implemented Features

JWT-based authentication

Role-based authorization

Authority-based permission system

Custom AuthenticationEntryPoint

Secure password encryption

CORS configuration

Dev & Prod profiles

Sanitized error responses

# 👥 Roles

ROLE_USER

ROLE_ADMIN

ROLE_SUPERADMIN

# 🔑 Authorities (Examples)

USER_READ

USER_WRITE

ADMIN_READ

ADMIN_WRITE

This structure enables fine-grained access control and enterprise-level permission scalability.

✅ DTO Validation

All incoming requests are validated using Bean Validation (@Valid).

Example:

@Size(min = 2, max = 100)
@NotBlank
private String lastName;

Validation errors are automatically intercepted and returned in a standardized JSON format.

# 🛑 Standardized Error Handling

The API uses a centralized @RestControllerAdvice to ensure consistent and secure error responses.

📦 Error Response Pattern
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
🔎 Handled HTTP Status Codes

400 → Validation errors

401 → Authentication failures (invalid/expired JWT)

403 → Access denied

404 → Resource not found

409 → Data conflicts (email/passport already exists)

500 → Internal server error (sanitized)

⚠ Stack traces are never exposed to the client.

⚙ Environment Configuration

The project supports multiple environments:

application-dev.yml

application-prod.yml

Configured Settings

Database connection

JWT secret & expiration

CORS configuration

Hibernate settings

Connection pool configuration

Profile activation

# 🛢 Database

PostgreSQL

JPA / Hibernate ORM

Unique constraints (email, passport)

Optimized connection pool

Clean entity relationships

📄 API Documentation

Interactive documentation available via Swagger:

http://localhost:8080/swagger-ui/index.html
🐳 Docker (Planned)

The system will be containerized with:

🗄 PostgreSQL container

☕ Spring Boot container

🔗 Docker bridge network

🌐 Port 8080 exposed

Planned Architecture
PostgreSQL Container
        ↕
Bridge Network
        ↕
Spring Boot Container
        ↕
localhost:8080
Benefits

Environment isolation

Scalable infrastructure

Easy deployment

Reproducible environments

🎯 Project Goals

Enterprise-grade security

Clean architecture

Expandable permission system

Modern backend standards

Containerized deployment

Production-level error handling

# 📌 Future Improvements

Full Docker & Docker Compose implementation

CI/CD pipeline

Unit & integration testing

Structured logging improvements

Monitoring with Spring Actuator

Rate limiting

Chat with WebSocket

Refresh token implementation

# 👨‍💻 Author

Lucas Macedo
Backend Developer focused on secure and scalable systems.

GitHub: https://github.com/devlucas-java

LinkedIn: https://www.linkedin.com/in/devlucas-java/

Instagram: https://www.instagram.com/devlucas_java/
