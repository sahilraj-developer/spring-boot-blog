package com.blog.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.blog.model.Category;
import com.blog.blog.service.CategoryService;

/**
 * CategoryController exposes REST endpoints to manage blog categories.
 * Supports creating, updating, retrieving, and deleting categories.
 * Categories help organize blogs by grouping related topics together.
 */
@RestController
@RequestMapping("/api/categories")
@CrossOrigin // Allows CORS support for frontend frameworks (React/Next.js/Vue)
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Constructor-based dependency injection
     * Recommended for required dependencies & easier unit testing.
     */
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Create a new category
     *
     * Example Request Body:
     * {
     *   "name": "Technology",
     *   "description": "Programming and tech updates"
     * }
     *
     * @param category JSON object mapped to Category model
     * @return Created category with 200 OK response
     */
    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        Category createdCategory = categoryService.create(category);
        return ResponseEntity.ok(createdCategory);
    }

    /**
     * Get all categories
     *
     * Example:
     * GET /api/categories
     *
     * @return list of categories
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    /**
     * Update an existing category
     *
     * @param id Category ID to update
     * @param category Updated category data
     * @return Updated data or 404 if category not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(
            @PathVariable String id,
            @RequestBody Category category
    ) {
        Category updatedCategory = categoryService.update(id, category);
        return ResponseEntity.ok(updatedCategory);
    }

    /**
     * Delete a category by ID
     *
     * @param id Category ID to delete
     * @return HTTP 204 No Content if successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
