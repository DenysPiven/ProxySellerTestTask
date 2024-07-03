package com.proxyseller.twitter.controller

import com.proxyseller.twitter.model.Post
import com.proxyseller.twitter.service.PostService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts")
@Api(value = "Post Controller", description = "Operations related to posts")
class PostController {

    @Autowired
    PostService postService

    @PostMapping("/")
    @ApiOperation(value = "Create a post", notes = "Creates a new post")
    ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createdPost = postService.createPost(post)
        return ResponseEntity.ok(createdPost)
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a post by ID", notes = "Retrieves a post by its ID")
    ResponseEntity<Post> getPostById(@PathVariable String id) {
        Post post = postService.findPostById(id)
        return ResponseEntity.ok(post)
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a post", notes = "Updates the content of an existing post")
    ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post updatedPost) {
        Post post = postService.updatePost(id, updatedPost)
        return ResponseEntity.ok(post)
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a post", notes = "Deletes the specified post")
    ResponseEntity<Void> deletePost(@PathVariable String id) {
        postService.deletePost(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{postId}/like")
    @ApiOperation(value = "Like a post", notes = "Likes the specified post")
    ResponseEntity<Post> likePost(@PathVariable String postId, @RequestParam String userId) {
        Post post = postService.likePost(postId, userId)
        return ResponseEntity.ok(post)
    }

    @PostMapping("/{postId}/unlike")
    @ApiOperation(value = "Unlike a post", notes = "Unlikes the specified post")
    ResponseEntity<Post> unlikePost(@PathVariable String postId, @RequestParam String userId) {
        Post post = postService.unlikePost(postId, userId)
        return ResponseEntity.ok(post)
    }

    @GetMapping("/feed/{userId}")
    @ApiOperation(value = "Get user feed", notes = "Gets the feed of posts from users that the specified user is following")
    ResponseEntity<List<Post>> getUserFeed(@PathVariable String userId) {
        List<Post> feed = postService.getUserFeed(userId)
        return ResponseEntity.ok(feed)
    }

    @GetMapping("/user/{userId}")
    @ApiOperation(value = "Get user posts", notes = "Gets the posts from the specified user")
    ResponseEntity<List<Post>> getUserPosts(@PathVariable String userId) {
        List<Post> posts = postService.getUserPosts(userId)
        return ResponseEntity.ok(posts)
    }
}
