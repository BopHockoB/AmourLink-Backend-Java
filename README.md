<p align="center">
  <h1>AmourLink-Backend-Java</h1>
  <p align="center">The robust, scalable Java microservices backbone for modern social and dating applications.</p>
  <p align="center">
    <a href="https://github.com/BopHockoB/AmourLink-Backend-Java/actions/workflows/ci.yml">
      <img src="https://github.com/BopHockoB/AmourLink-Backend-Java/actions/workflows/ci.yml/badge.svg" alt="Build Status">
    </a>
    <a href="https://github.com/BopHockoB/AmourLink-Backend-Java/blob/master/LICENSE">
      <img src="https://img.shields.io/github/license/BopHockoB/AmourLink-Backend-Java?style=flat-square" alt="License">
    </a>
    <a href="https://github.com/BopHockoB/AmourLink-Backend-Java/pulls">
      <img src="https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square" alt="PRs Welcome">
    </a>
    <a href="https://github.com/BopHockoB/AmourLink-Backend-Java">
      <img src="https://img.shields.io/github/stars/BopHockoB/AmourLink-Backend-Java?style=social" alt="GitHub stars">
    </a>
  </p>
</p>

---

## The Strategic "Why"

> Building a high-performance, secure, and scalable backend for social interaction platforms presents significant architectural challenges, often leading to monolithic designs that hinder agility and future growth. Ensuring seamless user experiences, managing diverse media, handling secure payments, and maintaining robust security across a global user base demands a sophisticated, distributed system.

AmourLink-Backend-Java addresses these complexities with a meticulously crafted microservices architecture. By decomposing the application into independent, manageable services, it provides a resilient, horizontally scalable foundation that accelerates development, simplifies maintenance, and ensures a superior, uninterrupted experience for end-users. This design enables rapid iteration, independent scaling of components, and enhanced fault isolation, making it the ideal choice for modern, data-intensive social applications.

## Key Features

AmourLink-Backend-Java is engineered with a comprehensive set of features to power dynamic social platforms:

*   ⚡️ **User Management**: Seamless registration, profile management, and interaction capabilities, providing a personalized experience for every user.
*   🔒 **Robust Security**: A dedicated `security-service` ensures secure authentication, granular authorization, and comprehensive data protection, safeguarding user privacy and platform integrity.
*   🖼️ **Media Handling**: Efficient storage, retrieval, and processing of user-generated content (images, videos) via the `media-service`, enabling rich multimedia interactions.
*   💸 **Subscription & Payments**: Flexible subscription models and secure payment processing capabilities, managed by the `subscription-service` and `payment-service`, facilitate monetization and premium features.
*   📧 **Email Notifications**: Reliable transactional and promotional email delivery through the `email-service`, keeping users informed and engaged.
*   ⚙️ **Dynamic Configuration**: Centralized configuration management for all microservices using the `config-server`, allowing for real-time updates without service restarts.
*   🌐 **Service Discovery**: Automated service registration and lookup for resilient inter-service communication, ensuring high availability and fault tolerance across the distributed system.

## Technical Architecture

This project leverages a modern Java-based microservices architecture, orchestrated for scalability and resilience.

### Tech Stack

| Technology      | Purpose                                     | Key Benefit                                          |
| :-------------- | :------------------------------------------ | :--------------------------------------------------- |
| **Java 17+**    | Primary development language                | Performance, robustness, vast ecosystem              |
| **Spring Boot** | Rapid application development framework     | Convention over configuration, embedded servers      |
| **Spring Cloud**| Microservices orchestration                 | Service Discovery, Config Server, API Gateway (other repo)|
| **Maven**       | Project build automation and dependency mgmt| Standardized build process, reproducible builds      |
| **Docker**      | Containerization of services                | Environment consistency, portability, isolation      |
| **Docker Compose** | Multi-container application definition   | Simplified local development and deployment          |

### Directory Structure

The repository is structured to reflect its microservices-oriented design:

```
.
├── 📁 .mvn/
├── 📁 config-server/
├── 📁 discovery/
├── 📁 email-service/
├── 📁 media-service/
├── 📁 payment-service/
├── 📁 security-service/
├── 📁 subscription-service/
├── 📁 user-service/
├── 📄 .gitignore
├── 📄 README.md
├── 📄 docker-compose.yml
├── 📄 mvnw
├── 📄 mvnw.cmd
└── 📄 pom.xml
```

## Operational Setup

Follow these steps to get AmourLink-Backend-Java up and running on your local machine.

### Prerequisites

Ensure you have the following installed:

*   **Java Development Kit (JDK) 17+**
*   **Apache Maven 3.6+**
*   **Docker Desktop** (includes Docker Engine and Docker Compose)

### Installation

1.  **Clone the Repository**:
    ```bash
    git clone https://github.com/BopHockoB/AmourLink-Backend-Java.git
    cd AmourLink-Backend-Java
    ```

2.  **Build All Microservices**:
    Navigate to the project root and build all services using Maven. This will compile the code and package each service into a JAR file.
    ```bash
    mvn clean install -DskipTests
    ```

3.  **Start Services with Docker Compose**:
    The `docker-compose.yml` file orchestrates all microservices, along with any necessary infrastructure components (like Eureka for discovery and Spring Cloud Config Server).
    ```bash
    docker-compose up -d
    ```
    This command will build Docker images (if not already built) and start all services in detached mode.

4.  **Verify Services**:
    You can check the status of your running containers:
    ```bash
    docker-compose ps
    ```
    Access the Eureka dashboard (typically at `http://localhost:8761`) to see registered services.

### Environment Configuration

The `config-server` service is responsible for centralizing configuration for all microservices. Each microservice fetches its configuration from the `config-server` at startup.

*   **Configuration Files**: Configuration for each service (e.g., database credentials, third-party API keys) is managed within the `config-server`'s repository (which is typically a Git repository).
*   **Local Overrides**: For local development, you can create `application-dev.yml` or `application.yml` files within each service's `src/main/resources` directory to override specific properties.
*   **Docker Compose Environment Variables**: Sensitive information or environment-specific settings can be passed to services via environment variables in `docker-compose.yml` or a `.env` file for the Docker Compose setup.

## Community & Governance

### Contributing

We welcome contributions to AmourLink-Backend-Java! To contribute, please follow these steps:

1.  **Fork** the repository.
2.  **Create a new branch** for your feature or bug fix: `git checkout -b feature/your-feature-name`.
3.  **Make your changes** and ensure they adhere to the project's coding standards.
4.  **Write clear, concise commit messages**.
5.  **Push your branch** to your forked repository.
6.  **Open a Pull Request** against the `main` branch of this repository, describing your changes in detail.

Your contributions are invaluable to the growth and improvement of this project.

### License

This project is licensed under the terms of the **[LICENSE](LICENSE)** file.

A summary of the license typically includes:

*   **Permissions**: You are free to use, modify, and distribute the software.
*   **Conditions**: You must include the original copyright and license notice in any substantial portions of the software.
*   **Limitations**: The software is provided "as is" without warranty of any kind. The authors or copyright holders are not liable for any claims, damages, or other liabilities arising from the use of the software.

Please refer to the full `LICENSE` file in the repository for complete details.
