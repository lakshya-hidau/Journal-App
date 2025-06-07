# Journal App

This is a Spring Boot-based Journal App that allows users to manage their journal entries. The application uses MongoDB for data storage, Redis for caching, and integrates with Google OAuth2 for authentication.

## Features
- User authentication and authorization using Spring Security and Google OAuth2.
- MongoDB integration for storing journal entries.
- Redis caching for improved performance.
- Weather API integration for fetching weather data.
- Email notifications using SMTP.

## Technologies Used
- Java
- Spring Boot
- Maven
- MongoDB
- Redis
- Google OAuth2
- Weather API
- SMTP Email

## Prerequisites
- Java 17 or higher
- Maven 3.8 or higher
- MongoDB installed locally or accessible remotely
- Redis server accessible remotely
- SMTP email credentials

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd journal-app


## Endpoints

# Authentication
POST /login - Authenticate user and obtain JWT token.

# User Management
POST /users - Create a new user.
GET /users/{id} - Get user details by ID.
PUT /users/{id} - Update user details.
DELETE /users/{id} - Delete user.

# Journal Entries
POST /journals - Create a new journal entry.
GET /journals - Get all journal entries.
GET /journals/{id} - Get journal entry by ID.
PUT /journals/{id} - Update journal entry.
DELETE /journals/{id} - Delete journal entry.

# Weather API
GET /weather - Fetch current weather data.

# Redis Cache
GET /cache/clear - Clear Redis cache.
