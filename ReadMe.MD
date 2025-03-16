# Ecommerce Analytics Platform

## Overview
This project is a **Microservices-based E-Commerce Analytics Platform** that allows users to manage orders, products, and analytics while providing personalized recommendations. The system follows an event-driven architecture using **Kafka** and is built with **Spring Boot**, **Java**, **PostgreSQL**, **MongoDB**, **Redis**, and **Docker**.

User authentication and authorization are managed using **Keycloak** with **PKCE (Proof Key for Code Exchange)**, eliminating the need for a separate User Management Service.

## Project Structure
```
Elite/
│── backend/
│   ├── service-registry/
│   ├── cloud-gateway/
│   ├── order-service/
│   ├── product-service/
│   ├── recommendation-service/
│   ├── analytics-service/
│   ├── event-logging-service/
│   ├── kafka-event-bus/
│── frontend/
│   ├── src/
│   ├── public/
│   ├── package.json
│   ├── .env
│── README.md
│── docker-compose.yml
│── .gitignore
│── keycloak/
│   ├── realm-export.json
```

## Microservices Overview
### 1. **Service Registry**
- Manages service discovery for microservices.
- Ensures dynamic service registration.
- Built using **Spring Cloud Eureka**.

### 2. **Cloud Gateway**
- Routes API requests to respective microservices.
- Provides centralized authentication, authorization, and rate limiting.
- Uses **Keycloak** to verify user authentication.
- Built using **Spring Cloud Gateway**.

### 3. **Order Management Service**
- Manages order processing, including order placement and status updates.
- Uses **PostgreSQL** to store orders.
- Publishes order events to **Kafka**.

### 4. **Product Catalog Service**
- Stores product details and stock availability.
- Uses **MongoDB** for product catalog management.
- Syncs product inventory with orders.

### 5. **Recommendation Service**
- Provides personalized product recommendations.
- Uses **Redis** to store frequently accessed recommendations.
- Consumes user behavior events from **Kafka**.

### 6. **Analytics Service**
- Collects and processes sales and customer data.
- Uses **MongoDB** to store analytics data.
- Generates reports and insights.

### 7. **Event Logging Service**
- Logs system-wide events for debugging and monitoring.
- Uses **MongoDB** for log storage.
- Listens to **Kafka** topics for event tracking.

### 8. **Kafka Event Bus**
- Enables asynchronous communication between services.
- Ensures event-driven processing of data changes.

### 9. **Keycloak Authentication**
- Handles authentication and authorization.
- Uses PKCE for secure OAuth2 authentication.
- Protects APIs using JWT tokens issued by Keycloak.

## Technologies Used
- **Backend**: Java, Spring Boot, Spring Data JPA, Spring Cloud (Eureka, Gateway)
- **Databases**: PostgreSQL, MongoDB, Redis
- **Message Queue**: Apache Kafka
- **Authentication & Authorization**: Keycloak with PKCE
- **Containerization**: Docker, Docker Compose
- **Frontend**: React with TypeScript (Vite or CRA)
- **State Management**: Redux Toolkit or Context API
- **Monitoring**: Prometheus, Grafana (Optional)

## Setup & Installation
### Prerequisites
- Java 21+
- Maven
- Docker & Docker Compose
- PostgreSQL & MongoDB (if running locally)
- Apache Kafka
- Node.js v20.17.0 (For Frontend)
- Keycloak (Standalone or Docker)

### Running the Services
#### 1. Clone the Repository
```sh
git clone https://github.com/RahulMikaelson/Elite.git
cd Elite
```

#### 2. Start Keycloak
You need to run Keycloak to handle authentication and authorization.

##### **Running Keycloak with Docker**
```sh
docker run -d --name keycloak -p 8181:8080   -e KEYCLOAK_ADMIN=admin   -e KEYCLOAK_ADMIN_PASSWORD=admin   quay.io/keycloak/keycloak:latest start-dev
```

##### **Importing Realm Configuration**
Once Keycloak is running, go to the admin console (`http://localhost:8181/admin`) and:
1. Login with `admin/admin`
2. Create a new realm
3. Import `realm-export.json` from the `keycloak/` directory

#### 3. Start Backend Services using Docker
```sh
docker-compose up -d
```

#### 4. Run Microservices Locally (Without Docker)
```sh
cd backend/service-registry
mvn spring-boot:run

cd backend/api-gateway
mvn spring-boot:run

cd backend/order-service
mvn spring-boot:run
```
(Similarly, run other services by navigating to respective folders)

#### 5. Start Frontend (React with TypeScript)
```sh
cd frontend
npm install
npm start
```


## API Documentation
Each microservice will have a **Swagger UI** for testing APIs:
- API Gateway: `http://localhost:8080/swagger-ui.html`
- Order Service: `http://localhost:8082/swagger-ui.html`
- Product Service: `http://localhost:8083/swagger-ui.html`

## Contribution Guidelines
1. Fork the repository.
2. Create a feature branch.
3. Commit changes with meaningful messages.
4. Open a pull request.

---

### Author
Developed by **Varun** 🚀
