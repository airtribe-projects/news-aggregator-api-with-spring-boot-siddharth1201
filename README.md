# News Aggregator API

A RESTful API for managing user news preferences and aggregating news content. Built with Spring Boot and Spring Security.

## Features

- User authentication and authorization
- JWT-based authentication
- CRUD operations for news preferences
- Personalized news feed based on user preferences
- Secure endpoints with role-based access control

## Tech Stack

- **Backend**: Spring Boot 3.x
- **Security**: Spring Security, JWT
- **Database**: H2 (for development, can be configured for production databases)
- **Build Tool**: Gradle

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Authenticate and get JWT token

### Preferences
- `GET /api/preference` - Get all preferences for current user
- `POST /api/preference` - Create a new preference
- `PUT /api/preference/{preferenceId}` - Update an existing preference

### News
- `GET /api/news` - Get personalized news feed based on user preferences

## Getting Started

1. **Prerequisites**
   - Java 17 or higher
   - Gradle 7.x or higher

2. **Configuration**
   - Copy `application.properties.example` to `application.properties`
   - Update the configuration with your database and API keys

3. **Build and Run**
   ```bash
   # Build the project
   ./gradlew build
   
   # Run the application
   ./gradlew bootRun
   ```

4. **Access the API**
   - The application will be available at `http://localhost:8080`
   - Use tools like Postman or curl to interact with the API

## Security

- All endpoints except `/api/auth/**` are secured
- Include JWT token in the `Authorization` header for authenticated requests
- Example: `Authorization: Bearer your.jwt.token.here`

## Contributing

1. Fork the repository
2. Create a new branch for your feature
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

