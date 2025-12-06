package com.blog.blog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.blog.blog.model.Blog;

public interface BlogRepository extends MongoRepository<Blog, String> {

    List<Blog> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);

    Page<Blog> findAll(Pageable pageable);
}
