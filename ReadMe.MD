Ecommerce Analytics Platform

## Overview
This project is a **Microservices-based E-Commerce Analytics Platform** that allows users to manage orders, products, and analytics while providing personalized recommendations. The system follows an event-driven architecture using **Kafka** and is built with **Spring Boot**, **Java**, **PostgreSQL**, **MongoDB**, **Redis**, and **Docker**.

## Project Structure
```
E-Commerce-Analytics-Platform/
│── backend/
│   ├── service-registry/
│   ├── api-gateway/
│   ├── user-service/
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
```

## Microservices Overview
### 1. **Service Registry**
- Manages service discovery for microservices.
- Ensures dynamic service registration.
- Built using **Spring Cloud Eureka**.

### 2. **API Gateway**
- Routes API requests to respective microservices.
- Provides centralized authentication and rate limiting.
- Built using **Spring Cloud Gateway**.

### 3. **User Management Service**
- Handles user authentication and account management.
- Stores user data in **PostgreSQL**.
- Exposes REST APIs for user registration and login.

### 4. **Order Management Service**
- Manages order processing, including order placement and status updates.
- Uses **PostgreSQL** to store orders.
- Publishes order events to **Kafka**.

### 5. **Product Catalog Service**
- Stores product details and stock availability.
- Uses **MongoDB** for product catalog management.
- Syncs product inventory with orders.

### 6. **Recommendation Service**
- Provides personalized product recommendations.
- Uses **Redis** to store frequently accessed recommendations.
- Consumes user behavior events from **Kafka**.

### 7. **Analytics Service**
- Collects and processes sales and customer data.
- Uses **MongoDB** to store analytics data.
- Generates reports and insights.

### 8. **Event Logging Service**
- Logs system-wide events for debugging and monitoring.
- Uses **MongoDB** for log storage.
- Listens to **Kafka** topics for event tracking.

### 9. **Kafka Event Bus**
- Enables asynchronous communication between services.
- Ensures event-driven processing of data changes.

## Technologies Used
- **Backend**: Java, Spring Boot, Spring Data JPA, Spring Cloud (Eureka, Gateway)
- **Databases**: PostgreSQL, MongoDB, Redis
- **Message Queue**: Apache Kafka
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

### Running the Services
#### 1. Clone the Repository
```sh
  git clone https://github.com/RahulMikaelson/Elite.git
  cd Elite
```
#### 2. Start Backend Services using Docker
```sh
docker-compose up -d
```
#### 3. Run Microservices Locally (Without Docker)
```sh
cd backend/service-registry
mvn spring-boot:run

cd backend/api-gateway
mvn spring-boot:run

cd backend/user-service
mvn spring-boot:run
```
(Similarly, run other services by navigating to respective folders)

#### 4. Start Frontend (React with TypeScript)
```sh
cd frontend
npm install
npm start
```

## API Documentation
Each microservice will have a **Swagger UI** for testing APIs:
- API Gateway: `http://localhost:8080/swagger-ui.html`
- User Service: `http://localhost:8081/swagger-ui.html`
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


