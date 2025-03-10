# URL Shortener API

A RESTful API for a URL shortening service. This API allows users to:

- Create a new short URL
- Retrieve an original URL from a short URL
- Update an existing short URL
- Delete an existing short URL
- Get statistics on the short URL 

This repository provides an implementation of the URL shortener project from roadmap.sh: 

- https://roadmap.sh/projects/url-shortening-service

## Technologies Used

- **Java 21** programming language
- **Spring Boot 3.4** Java framework
- **MariaDB 11.7.2-ubi9** database
- **Maven** Dependency management
- **Docker** Deployment database

## Installation Guide

1. **Clone the repository**
   ```sh
   git clone https://github.com/Gill2602/URL-Shortening-Service
   cd URL-Shortening-Service
   ```

2. **Configure the Database**
   - Modify `docker-compose.yml` with your preferred credentials and run it.
   - Update `application.properties` with your database credentials.

3. **Build and Run the Project**
   ```sh
   mvn spring-boot:run
   ```

## API Endpoints

### Create Short URL
**POST** `/shorten`
#### Request Body:
```json
{
  "url": "https://www.youtube.com/"
}
```
#### Response:
```json
{
  "id": "1478",
  "url": "https://www.youtube.com/",
  "shortCode": "5C6",
  "createdAt": "2021-09-01T12:00:00.0000000",
  "updatedAt": "2021-09-01T12:00:00.0000000"
}
```

### Retrieve Original URL
**GET** `/shorten/{shortCode}`
#### Response:
```json
{
  "id": "1478",
  "url": "https://www.youtube.com/",
  "shortCode": "5C6",
  "createdAt": "2021-09-01T12:00:00.0000000",
  "updatedAt": "2021-09-01T12:00:00.0000000"
}
```

### Update Short URL
**PUT** `/shorten/{shortCode}`
#### Request Body:
```json
{
  "url": "https://roadmap.sh"
}
```
#### Response:
```json
{
  "id": "1",
  "url": "https://roadmap.sh",
  "shortCode": "abc123",
  "createdAt": "2021-09-01T12:00:00.0000000",
  "updatedAt": "2021-09-01T12:25:00.0000000"
}
```

### Delete Short URL
**DELETE** `/shorten/{shortCode}`
#### Response:
- **204 No Content** (if deleted successfully)
- **404 Not Found** (if the short URL does not exist)

### Get URL Statistics
**GET** `/shorten/{shortCode}/stats`
#### Response:
```json
{
  "id": "1",
  "url": "https://roadmap.sh/projects/url-shortening-service",
  "shortCode": "abc123",
  "createdAt": "2021-09-01T12:00:00.0000000",
  "updatedAt": "2021-09-01T12:00:00.0000000", 
  "lastAccessAt": "2022-02-26T14:30:00.0000000",
  "accessCount": 10
}
```

---


