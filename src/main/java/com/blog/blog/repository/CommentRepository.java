package com.blog.blog.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.blog.blog.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findByBlogIdOrderByCreatedAtDesc(String blogId);
}
