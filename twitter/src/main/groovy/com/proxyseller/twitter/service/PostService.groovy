package com.proxyseller.twitter.service

import com.proxyseller.twitter.model.Post
import com.proxyseller.twitter.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PostService {

    PostRepository postRepository

    @Autowired
    PostService(PostRepository postRepository) {
        this.postRepository = postRepository
    }

    Post createPost(Post post) {
        return postRepository.save(post)
    }

    Post findPostById(String id) {
        return postRepository.findById(id).orElseThrow { new RuntimeException("Post not found") }
    }

    Post updatePost(String id, Post updatedPost) {
        Post post = postRepository.findById(id).orElseThrow { new RuntimeException("Post not found") }
        post.content = updatedPost.content
        return postRepository.save(post)
    }

    void deletePost(String id) {
        postRepository.deleteById(id)
    }

    Post likePost(String postId, String userId) {
        Post post = postRepository.findById(postId).orElseThrow { new RuntimeException("Post not found") }
        if (!post.likes.contains(userId)) {
            post.likes.add(userId)
            return postRepository.save(post)
        }
        return post
    }

    Post unlikePost(String postId, String userId) {
        Post post = postRepository.findById(postId).orElseThrow { new RuntimeException("Post not found") }
        if (post.likes.contains(userId)) {
            post.likes.remove(userId)
            return postRepository.save(post)
        }
        return post
    }

    List<Post> getUserFeed(List<String> userIds) {
        return postRepository.findByUserIdIn(userIds)
    }

    List<Post> getUserPosts(String userId) {
        return postRepository.findByUserId(userId)
    }
}