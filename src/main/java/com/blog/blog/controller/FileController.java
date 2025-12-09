package com.blog.blog.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.blog.service.FileStorageService;

/**
 * FileController handles file upload functionality for the blog application.
 * It accepts multipart form-data uploads, stores the file on disk, and returns
 * the downloadable / accessible URL path which can be stored with the blog entity.
 */
@RestController
@RequestMapping("/api/files")
@CrossOrigin // Optional: allows frontend access (React/Next.js/Vue/etc.)
public class FileController {

    private final FileStorageService fileStorageService;

    /**
     * Constructor-based dependency injection
     * Helps improve testability and maintainability.
     */
    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    /**
     * Upload a file and return its public access URL.
     *
     * Endpoint: POST /api/files/upload
     *
     * Request Type: multipart/form-data
     * Example form-data:
     *   file: <select file>
     *
     * Successful Response Example:
     * {
     *   "url": "/uploads/abc123.png"
     * }
     *
     * Error Response Example:
     * {
     *   "error": "File upload failed: reason"
     * }
     *
     * @param file The file sent by the client
     * @return ResponseEntity with URL or error message
     */
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {

        Map<String, String> response = new HashMap<>();

        try {
            // Delegate saving logic to FileStorageService
            String fileUrl = fileStorageService.storeFile(file);

            // Put the response data in JSON object
            response.put("url", fileUrl);

            // Return success response
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            // Handle unexpected errors (disk write failure, etc.)
            response.put("error", "File upload failed: " + e.getMessage());
            return ResponseEntity.status(500).body(response); // Internal server error
        }
    }
}
