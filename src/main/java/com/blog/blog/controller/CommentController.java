package com.blog.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.blog.model.Comment;
import com.blog.blog.service.CommentService;

/**
 * CommentController manages all operations related to comments on blog posts.
 * It exposes REST endpoints for adding, fetching and deleting comments.
 */
@RestController
@RequestMapping("/api/comments")
@CrossOrigin // Enables CORS for frontend applications (React, Angular, Vue, Next.js)
public class CommentController {

    private final CommentService commentService;

    /**
     * Constructor-based dependency injection — recommended approach.
     */
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Add a new comment to a blog post.
     *
     * Example Request Body:
     * {
     *   "blogId": "678f06fe23bc4321d1234567",
     *   "userId": "user_123",
     *   "content": "This blog is very helpful!"
     * }
     *
     * @param comment The Comment request object from client
     * @return the created Comment object along with HTTP 200 OK
     */
    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {
        Comment created = commentService.addComment(comment);
        return ResponseEntity.ok(created);
    }

    /**
     * Fetch all comments for a specific blog.
     *
     * Example:
     * GET /api/comments/blog/678f06fe23bc4321d1234567
     *
     * @param blogId ID of the blog for which comments are needed
     * @return List of comments sorted by most recent
     */
    @GetMapping("/blog/{blogId}")
    public ResponseEntity<List<Comment>> getCommentsForBlog(@PathVariable String blogId) {
        return ResponseEntity.ok(commentService.getCommentsForBlog(blogId));
    }

    /**
     * Delete a comment by its ID.
     *
     * Example:
     * DELETE /api/comments/897fg67db238c123
     *
     * @param id comment ID to delete
     * @return No content response (HTTP 204) after deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable String id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }
}
