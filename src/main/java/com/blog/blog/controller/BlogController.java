package com.blog.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.blog.model.Blog;
import com.blog.blog.service.BlogService;

/**
 * BlogController handles all HTTP operations for managing blog posts.
 * It exposes RESTful API endpoints for creating, reading, updating,
 * deleting, searching, and paginating blog data.
 */
@RestController
@RequestMapping("/api/blogs")
@CrossOrigin // Allows access from frontend apps like React/Next.js
public class BlogController {

    private final BlogService blogService;

    /**
     * Constructor-based dependency injection.
     * Preferred over field injection for testability and immutability.
     */
    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * Create a new blog post.
     * If the client does not provide an imageUrl, we set an empty string
     * to avoid null pointer issues.
     *
     * @param blog - Blog object from request body
     * @return ResponseEntity with created blog and HTTP 200 status
     */
    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {

        // Prevent storing null imageUrl in DB
        if (blog.getImageUrl() == null) {
            blog.setImageUrl("");
        }

        Blog created = blogService.saveBlog(blog);
        return ResponseEntity.ok(created); // Return 200 OK + body
    }

    /**
     * Fetch all blogs (without pagination).
     * Useful for small data sets or admin usage.
     *
     * Endpoint: GET /api/blogs/all
     */
    @GetMapping("/all")
    public ResponseEntity<List<Blog>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    /**
     * Fetch a single blog by its unique ID.
     *
     * @param id - Blog ID from path
     * @return Blog if found, otherwise 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable String id) {
        return blogService.getBlogById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());  // 404 response
    }

    /**
     * Update an existing blog.
     * Only title, description, and image fields are allowed to change.
     *
     * @param id - Blog ID
     * @param blog - Updated blog data
     * @return Updated blog data
     */
    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(
            @PathVariable String id,
            @RequestBody Blog blog
    ) {
        Blog updated = blogService.updateBlog(id, blog);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a blog by ID.
     *
     * @param id - Blog ID to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable String id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    /**
     * Retrieve blogs using pagination and sorting.
     * Example: GET /api/blogs?page=0&size=10&sortBy=createdAt
     *
     * @param page page number (0-based index)
     * @param size number of records per page
     * @param sortBy field to sort by
     * @return paginated list of blogs inside Page<Blog>
     */
    @GetMapping
    public ResponseEntity<Page<Blog>> getBlogsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        return ResponseEntity.ok(blogService.getBlogsPaged(page, size, sortBy));
    }

    /**
     * Search blogs by title or description.
     * Example: GET /api/blogs/search?keyword=spring
     *
     * @param keyword text to search for
     * @return list of matching blogs
     */
    @GetMapping("/search")
    public ResponseEntity<List<Blog>> searchBlogs(@RequestParam String keyword) {
        return ResponseEntity.ok(blogService.searchBlogs(keyword));
    }
}
