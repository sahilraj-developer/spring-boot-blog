package com.blog.blog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.blog.blog.model.Like;

public interface LikeRepository extends MongoRepository<Like, String> {

    boolean existsByBlogIdAndUserId(String blogId, String userId);

    void deleteByBlogIdAndUserId(String blogId, String userId);

    long countByBlogId(String blogId);
}
