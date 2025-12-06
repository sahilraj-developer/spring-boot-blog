package com.blog.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.blog.model.Like;
import com.blog.blog.repository.LikeRepository;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    public long like(String blogId, String userId) {
        boolean alreadyLiked = likeRepository.existsByBlogIdAndUserId(blogId, userId);
        if (!alreadyLiked) {
            Like like = new Like(blogId, userId);
            likeRepository.save(like);
        }
        return likeRepository.countByBlogId(blogId);
    }

    public long unlike(String blogId, String userId) {
        likeRepository.deleteByBlogIdAndUserId(blogId, userId);
        return likeRepository.countByBlogId(blogId);
    }

    public long getLikeCount(String blogId) {
        return likeRepository.countByBlogId(blogId);
    }
}
