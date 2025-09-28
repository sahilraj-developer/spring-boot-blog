package com.blog.blog.repository;

import com.blog.blog.model.Blog; // corrected import
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends MongoRepository<Blog, String> {
}
