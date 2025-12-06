package com.blog.blog.service;

import com.blog.blog.model.Blog;
import com.blog.blog.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    private final BlogRepository blogRepository;

    @Autowired  // optional with single constructor but good practice
    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    // Create blog
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    // Get all blogs (sorted by creation date desc)
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    // Get blog by ID
    public Optional<Blog> getBlogById(String id) {
        return blogRepository.findById(id);
    }

    // Update blog
    public Blog updateBlog(String id, Blog updatedBlog) {
        return blogRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(updatedBlog.getTitle());
                    existing.setDescription(updatedBlog.getDescription());
                    existing.setImageUrl(updatedBlog.getImageUrl());
                    return blogRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));
    }

    // Delete blog
    public void deleteBlog(String id) {
        if (!blogRepository.existsById(id)) {
            throw new RuntimeException("Blog not found with id: " + id);
        }
        blogRepository.deleteById(id);
    }

    // Search by title or description
    public List<Blog> searchBlogs(String keyword) {
        return blogRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }

    // Pagination + Sorting
    public Page<Blog> getBlogsPaged(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return blogRepository.findAll(pageable);
    }
}
