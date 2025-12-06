package com.blog.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.blog.model.Comment;
import com.blog.blog.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsForBlog(String blogId) {
        return commentRepository.findByBlogIdOrderByCreatedAtDesc(blogId);
    }

    public void deleteComment(String id) {
        commentRepository.deleteById(id);
    }
}
