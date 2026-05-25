# AmourLink Backend (Java)

Backend API for the AmourLink platform built with Java and Spring Boot.  
This project provides authentication, user management, messaging, and core backend services for a social/dating application architecture.

Repository: https://github.com/BopHockoB/AmourLink-Backend-Java

---

## Overview

AmourLink Backend is a Java Spring Boot REST API designed to support a modern social networking or dating platform. The application follows a layered backend architecture with RESTful endpoints, database persistence, authentication handling, and scalable service organization.

The backend is responsible for:
- User authentication & authorization
- User profile management
- Relationship/matching functionality
- Messaging infrastructure
- Persistent database storage
- API communication with frontend applications

---

## Features

### Authentication System
- User registration
- Secure login
- JWT/session-based authentication
- Password encryption

### User Management
- User profiles
- Profile updates
- Account management
- User search/filtering

### Social Features
- Matching system
- Friend/connection interactions
- Messaging/chat support
- Relationship handling

### Backend Infrastructure
- RESTful API architecture
- Service-oriented design
- Repository/database abstraction
- Exception handling
- DTO-based request/response handling

---

## Technologies Used

### Backend Framework
- Java
- Spring Boot
- Spring Web

### Database & Persistence
- Spring Data JPA
- Hibernate
- SQL Database (MySQL/PostgreSQL)

### Security
- Spring Security
- JWT Authentication
- Password hashing/encryption

### Build Tools
- Maven

### Additional Tools
- Lombok
- Validation API
- Jackson JSON processing

---

## Project Architecture

The project follows a layered architecture pattern:

```text
src/main/java/
│
├── controller/     # REST API endpoints
├── service/        # Business logic
├── repository/     # Database access layer
├── model/entity/   # JPA entities
├── dto/            # Request/response DTOs
├── security/       # Authentication & JWT logic
├── config/         # Application configuration
└── exception/      # Global exception handling
```

---

## Core Functionalities

### 1. Authentication Flow

The backend handles:
- User signup
- Login authentication
- Credential verification
- Token generation
- Session authorization

### 2. Database Management

The application uses ORM-based persistence with:
- Entity relationships
- Repository abstraction
- CRUD operations
- Query management

### 3. REST API

RESTful endpoints provide:
- JSON request/response communication
- Client-server interaction
- Frontend integration support

---

## API Design

Example API endpoint structure:

```text
/api/auth/register
/api/auth/login
/api/users
/api/profile
/api/messages
/api/matches
```

---

## Installation

Clone the repository:

```bash
git clone https://github.com/BopHockoB/AmourLink-Backend-Java.git
cd AmourLink-Backend-Java
```

---

## Requirements

Before running the project, ensure you have installed:

- Java 17+ (or project-compatible version)
- Maven
- MySQL/PostgreSQL
- Git

---

## Environment Configuration

Configure your database connection inside:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/amourlink
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## Running the Application

### Using Maven

```bash
./mvnw spring-boot:run
```

or

```bash
mvn spring-boot:run
```

---

## Build the Project

```bash
mvn clean install
```

---

## API Testing

You can test endpoints using:
- Postman
- Insomnia
- Swagger UI (if configured)

Example local API URL:

```text
http://localhost:8080
```

---

## Example Request

### Register User

```http
POST /api/auth/register
```

Example JSON body:

```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

---

## Security Features

- Password encryption
- Secure authentication flow
- Protected API endpoints
- JWT token validation
- Request validation

---

## Future Improvements

Potential future enhancements include:

- Real-time chat with WebSockets
- Push notifications
- OAuth2 social login
- Docker deployment
- CI/CD pipelines
- Cloud deployment support
- Media/file upload support
- AI-powered matching system

---

## Repository Structure

```text
AmourLink-Backend-Java/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
│
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---

## Development Goals

This project demonstrates:
- Backend API development
- Java Spring Boot architecture
- Authentication & authorization systems
- Database integration
- REST API engineering
- Scalable backend structure

---

## License

This project is intended for educational and development purposes.
