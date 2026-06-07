# Kumiai RPG API

A Spring Boot REST API designed for the Kumiai GO Learn desktop application and website.

## Table of Contents
- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

## Overview
The Kumiai GO Learn API provides backend services for the Kumiai GO Learn ecosystem. For use cases and features of the desktop app, see the corresponding desktop application repository.

## Prerequisites
- Java 11 or higher
- Maven 3.6 or higher
- MariaDB 10.5 or higher

## Installation
1. Clone the repository
2. Install Maven dependencies:
   ```bash
   mvn clean install
   ```
3. Create `src/main/resources/application.properties` (see Configuration section below)
4. Build the project:
   ```bash
   mvn spring-boot:run
   ```

## Configuration
Configure `src/main/resources/application.properties` with your database and JWT settings:

```properties
# Hibernate - Database schema management
spring.jpa.hibernate.ddl-auto=update

# Database Connection
spring.datasource.url=jdbc:mysql://your-db-url
spring.datasource.username=your-db-username
spring.datasource.password=your-db-password

# JWT Authentication
jwt.secret=your-generated-jwt-secret
jwt.expiration=86400000
```

### Configuration Details
- **spring.jpa.hibernate.ddl-auto**: Set to `update` for automatic schema updates (use `create-drop` for development)
- **spring.datasource.url**: Replace `your-db-url` with your MariaDB connection URL
- **spring.datasource.username**: Your database user
- **spring.datasource.password**: Your database password
- **jwt.secret**: Generate a secure JWT secret for your API version
- **jwt.expiration**: Token validity duration in milliseconds (86400000 = 24 hours)

**Note:** We recommend using MariaDB for database compatibility with the entire ecosystem.

## Usage
Here is and example of a GET call with the user ID in URL ( note : all IDs are in UUID for safety reasons )
```bash
{
  "id": "string",
  "email": "string",
  "password": "string",
  "username": "string",
  "userlvl": "N5",
  "abonnement": "GRATUIT"
}
```

## API Endpoints
To use the api endpoints, start the api and user Swagger to see all available endpoints and their working states with the url :
```bash
http://localhost:8080/swagger-ui/index.html
```
And search for:
```bash
/v3/api-docs
```
## Troubleshooting
Common issues and solutions:
- **Database connection failed**: Verify MariaDB is running and credentials are correct
- **JWT token invalid**: Ensure `jwt.secret` is properly configured
- **Port already in use**: Configure `server.port` in application.properties

## Contributing
Contributions are welcome! Please follow the project's coding standards and submit pull requests with clear descriptions.

## License

Thank you for using Kumiai GO Learn API. Good luck!
