package com.proxyseller.twitter.controller

import com.proxyseller.twitter.model.Post
import com.proxyseller.twitter.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts")
class PostController {

    @Autowired
    PostService postService

    @PostMapping("/")
    ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createdPost = postService.createPost(post)
        return ResponseEntity.ok(createdPost)
    }

    @GetMapping("/{id}")
    ResponseEntity<Post> getPostById(@PathVariable String id) {
        Post post = postService.findPostById(id)
        return ResponseEntity.ok(post)
    }

    @PutMapping("/{id}")
    ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post updatedPost) {
        Post post = postService.updatePost(id, updatedPost)
        return ResponseEntity.ok(post)
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletePost(@PathVariable String id) {
        postService.deletePost(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{postId}/like")
    ResponseEntity<Post> likePost(@PathVariable String postId, @RequestParam String userId) {
        Post post = postService.likePost(postId, userId)
        return ResponseEntity.ok(post)
    }

    @PostMapping("/{postId}/unlike")
    ResponseEntity<Post> unlikePost(@PathVariable String postId, @RequestParam String userId) {
        Post post = postService.unlikePost(postId, userId)
        return ResponseEntity.ok(post)
    }

    @GetMapping("/feed/{userId}")
    ResponseEntity<List<Post>> getUserFeed(@PathVariable String userId) {
        List<Post> feed = postService.getUserFeed(userId)
        return ResponseEntity.ok(feed)
    }

    @GetMapping("/user/{userId}")
    ResponseEntity<List<Post>> getUserPosts(@PathVariable String userId) {
        List<Post> posts = postService.getUserPosts(userId)
        return ResponseEntity.ok(posts)
    }
}