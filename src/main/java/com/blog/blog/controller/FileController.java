package com.blog.blog.controller;

import com.blog.blog.service.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    /**
     * Upload a file and return its relative URL.
     *
     * Endpoint: POST /api/files/upload
     * Request: form-data, key = file, value = file to upload
     * Response: JSON { "url": "/uploads/filename.ext" }
     */
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            // Save file using FileStorageService
            String fileUrl = fileStorageService.storeFile(file);

            // Return JSON with file URL
            response.put("url", fileUrl);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.put("error", "File upload failed: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
