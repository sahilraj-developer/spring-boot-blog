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

@RestController
@RequestMapping("/api/blogs")
@CrossOrigin // optional - for frontend
public class BlogController {

    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    // CREATE BLOG
    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
        if (blog.getImageUrl() == null) {
            blog.setImageUrl("");
        }
        Blog created = blogService.saveBlog(blog);
        return ResponseEntity.ok(created);
    }

    // GET ALL BLOGS
    @GetMapping("/all")
    public ResponseEntity<List<Blog>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    // GET BLOG BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable String id) {
        return blogService.getBlogById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE BLOG
    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(
            @PathVariable String id,
            @RequestBody Blog blog
    ) {
        Blog updated = blogService.updateBlog(id, blog);
        return ResponseEntity.ok(updated);
    }

    // DELETE BLOG
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable String id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }

    // PAGINATION & SORTING
    @GetMapping
    public ResponseEntity<Page<Blog>> getBlogsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        return ResponseEntity.ok(blogService.getBlogsPaged(page, size, sortBy));
    }

    // SEARCH BLOGS
    @GetMapping("/search")
    public ResponseEntity<List<Blog>> searchBlogs(@RequestParam String keyword) {
        return ResponseEntity.ok(blogService.searchBlogs(keyword));
    }
}
