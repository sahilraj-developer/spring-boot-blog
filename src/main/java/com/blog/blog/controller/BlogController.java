package com.blog.blog.controller;

import com.blog.blog.model.Blog;
import com.blog.blog.service.BlogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * Create a new blog.
     * If imageUrl is not provided, it will be saved as an empty string.
     *
     * Endpoint: POST /api/blogs
     * Request body (JSON):
     * {
     *   "title": "My First Blog",
     *   "description": "This is a test blog",
     *   "imageUrl": "/uploads/filename.png" // optional
     * }
     * Response: Blog object saved in MongoDB
     */
    @PostMapping
    public Blog createBlog(@RequestBody Blog blog) {
        if (blog.getImageUrl() == null) {
            blog.setImageUrl(""); // default empty string if no image provided
        }
        return blogService.saveBlog(blog);
    }

    /**
     * Get all blogs.
     *
     * Endpoint: GET /api/blogs
     * Response: List of all blogs in MongoDB
     */
    @GetMapping
    public List<Blog> getAllBlogs() {
        return blogService.getAllBlogs();
    }
}
