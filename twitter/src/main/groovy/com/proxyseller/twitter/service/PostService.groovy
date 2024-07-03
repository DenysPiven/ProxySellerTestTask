package com.proxyseller.twitter.service

import com.proxyseller.twitter.model.Post
import com.proxyseller.twitter.model.User
import com.proxyseller.twitter.repository.PostRepository
import com.proxyseller.twitter.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PostService {

    @Autowired
    PostRepository postRepository

    @Autowired
    UserRepository userRepository

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

    List<Post> getUserFeed(String userId) {
        User user = userRepository.findById(userId).orElseThrow { new RuntimeException("User not found") }
        return postRepository.findByUserIdIn(user.following)
    }

    List<Post> getUserPosts(String userId) {
        return postRepository.findByUserId(userId)
    }
}