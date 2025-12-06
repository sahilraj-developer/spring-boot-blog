package com.blog.blog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.blog.blog.model.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
