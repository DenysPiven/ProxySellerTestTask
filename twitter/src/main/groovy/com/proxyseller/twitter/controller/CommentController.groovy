package com.proxyseller.twitter.controller

import com.proxyseller.twitter.model.Comment
import com.proxyseller.twitter.service.CommentService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comments")
@Api(value = "Comment Controller", description = "Operations related to comments")
class CommentController {

    @Autowired
    CommentService commentService

    @PostMapping("/")
    @ApiOperation(value = "Create a comment", notes = "Creates a new comment on a post")
    ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        Comment createdComment = commentService.createComment(comment)
        return ResponseEntity.ok(createdComment)
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a comment by ID", notes = "Retrieves a comment by its ID")
    ResponseEntity<Comment> getCommentById(@PathVariable String id) {
        Comment comment = commentService.findCommentById(id)
        return ResponseEntity.ok(comment)
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a comment", notes = "Updates the content of an existing comment")
    ResponseEntity<Comment> updateComment(@PathVariable String id, @RequestBody Comment updatedComment) {
        Comment comment = commentService.updateComment(id, updatedComment)
        return ResponseEntity.ok(comment)
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a comment", notes = "Deletes the specified comment")
    ResponseEntity<Void> deleteComment(@PathVariable String id) {
        commentService.deleteComment(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/post/{postId}")
    @ApiOperation(value = "Get comments for a post", notes = "Retrieves all comments for a specific post")
    ResponseEntity<List<Comment>> getCommentsForPost(@PathVariable String postId) {
        List<Comment> comments = commentService.getCommentsForPost(postId)
        return ResponseEntity.ok(comments)
    }
}
