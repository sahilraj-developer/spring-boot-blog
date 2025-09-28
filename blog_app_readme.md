# Blog Application (Spring Boot + MongoDB + File Upload)

---

## Table of Contents

1. [Project Overview](#project-overview)
2. [Technologies Used](#technologies-used)
3. [Folder Structure](#folder-structure)
4. [Installation & Setup](#installation--setup)
5. [Configuration](#configuration)
6. [Running the Application](#running-the-application)
7. [API Documentation](#api-documentation)
8. [Sample Data](#sample-data)
9. [Checklists](#checklists)
10. [Additional Features](#additional-features)
11. [Notes](#notes)

---

## Project Overview

This project is a **Blog application** built with **Spring Boot** and **MongoDB**. It allows users to:

- Create, read, update, and delete blog posts.
- Upload images for blogs.
- Store uploaded files locally and access them via URL.
- Store blog data (title, description, image link) in MongoDB.
- Handle missing image links by defaulting to empty strings.

---

## Technologies Used

- Java 17+
- Spring Boot 3.5.6
- Spring Web
- Spring Data MongoDB
- Maven
- MongoDB (local or Atlas)
- IntelliJ IDEA (IDE)
- Postman / Swagger (for testing APIs)

---

## Folder Structure

```
blog/
├── src/main/java/com/blog/blog/
│   ├── BlogApplication.java            # Main Spring Boot application
│   ├── model/
│   │   └── Blog.java                   # MongoDB document entity
│   ├── repository/
│   │   └── BlogRepository.java         # MongoDB repository
│   ├── service/
│   │   ├── BlogService.java            # Business logic for blogs
│   │   └── FileStorageService.java     # Logic for file uploads
│   └── controller/
│       ├── BlogController.java         # CRUD API endpoints for blogs
│       └── FileController.java         # API endpoint for file uploads
├── src/main/resources/
│   ├── application.properties          # Configuration file
│   └── uploads/                        # Folder for uploaded files
├── src/test/java/com/blog/blog/
│   └── BlogApplicationTests.java       # Test class
└── pom.xml                             # Maven dependencies and plugins
```

**Types of files:**

| Type        | Purpose |
|------------|---------|
| `.java`    | Source code (models, services, controllers) |
| `.properties` | Application configuration (MongoDB URL, upload folder) |
| `.md`      | Documentation (README, API notes) |
| `/uploads` | Folder to store uploaded files |

---

## Installation & Setup

1. Clone the repository:

```bash
git clone <your-repo-url>
cd blog
```

2. Ensure **MongoDB** is running (local or cloud).  

3. Update `application.properties`:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/blogdb
file.upload-dir=uploads
server.port=8080
```

4. Build the project:

```bash
mvn clean install
```

---

## Running the Application

```bash
mvn spring-boot:run
```

Application runs on: `http://localhost:8080`

---

## Configuration

- **MongoDB URI:** `spring.data.mongodb.uri`  
- **Upload folder:** `file.upload-dir`  
- **Serving static files:** Use `WebMvcConfig`:

```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}
```

---

## API Documentation

**Base URL:** `http://localhost:8080/api`

### 1️⃣ Blog APIs

| Method | Endpoint           | Request Body | Response | Description |
|--------|------------------|-------------|---------|-------------|
| POST   | `/blogs`          | `{ "title": "...", "description": "...", "imageUrl": "..." }` | Created blog JSON | Create a new blog |
| GET    | `/blogs`          | None        | List of blogs JSON | Get all blogs |
| GET    | `/blogs/{id}`     | None        | Blog JSON | Get a blog by ID |
| PUT    | `/blogs/{id}`     | JSON blog   | Updated blog JSON | Update a blog |
| DELETE | `/blogs/{id}`     | None        | Success message | Delete a blog |

### 2️⃣ File Upload APIs

| Method | Endpoint           | Request | Response | Description |
|--------|------------------|--------|---------|-------------|
| POST   | `/files/upload`   | multipart form `file=<image>` | `{ "url": "/uploads/<filename>" }` | Upload an image file and get URL |

**Access uploaded file:**  
`http://localhost:8080/uploads/<filename>`

---

## Sample Data

**POST /blogs**

```json
{
  "title": "My First Blog",
  "description": "This is a sample blog",
  "imageUrl": "/uploads/sample.png"
}
```

**GET /blogs**

```json
[
  {
    "id": "653f1c6e3b4b5c1234567890",
    "title": "My First Blog",
    "description": "This is a sample blog",
    "imageUrl": "/uploads/sample.png"
  }
]
```

---

## Checklists

✅ **Before Running**:

- [ ] MongoDB service running  
- [ ] Upload folder exists or configured in `application.properties`  
- [ ] Maven dependencies installed (`mvn clean install`)  

✅ **During Development**:

- [ ] Packages are consistent (`com.blog.blog.*`)  
- [ ] Controller routes annotated correctly (`@RestController`, `@RequestMapping`)  
- [ ] Services annotated with `@Service`  
- [ ] Repository extends `MongoRepository`  

✅ **Testing**:

- [ ] `BlogApplicationTests` configured with `@SpringBootTest(classes = BlogApplication.class)`  
- [ ] Can create, read, update, delete blogs  
- [ ] Can upload and access images  

---

## Additional Features

- Validation can be added for blog fields (title, description).  
- Error handling using `@ControllerAdvice` to send proper JSON error messages.  
- Pagination and sorting for `/blogs` GET endpoint.  
- Swagger/OpenAPI integration for interactive API documentation.  
- Dockerization support for containerized deployment.  
- Unit and integration tests for services and controllers.

---

## Notes

- `@Document` → Marks a class as MongoDB document.  
- `@Id` → Primary key field.  
- `FileStorageService` → Handles uploading files and generating filenames.  
- `/uploads` folder → Stores images locally; can be served with `WebMvcConfig`.  
- Use `BlogController` for CRUD operations and `FileController` for file uploads.  
- API can be tested using **Postman** or **Swagger** (optional).

