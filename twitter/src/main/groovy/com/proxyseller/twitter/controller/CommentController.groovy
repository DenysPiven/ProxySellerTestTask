package com.proxyseller.twitter.controller

import com.proxyseller.twitter.model.Comment
import com.proxyseller.twitter.service.CommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comments")
class CommentController {

    @Autowired
    CommentService commentService

    @PostMapping("/")
    ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        Comment createdComment = commentService.createComment(comment)
        return ResponseEntity.ok(createdComment)
    }

    @GetMapping("/{id}")
    ResponseEntity<Comment> getCommentById(@PathVariable String id) {
        Comment comment = commentService.findCommentById(id)
        return ResponseEntity.ok(comment)
    }

    @PutMapping("/{id}")
    ResponseEntity<Comment> updateComment(@PathVariable String id, @RequestBody Comment updatedComment) {
        Comment comment = commentService.updateComment(id, updatedComment)
        return ResponseEntity.ok(comment)
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteComment(@PathVariable String id) {
        commentService.deleteComment(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/post/{postId}")
    ResponseEntity<List<Comment>> getCommentsForPost(@PathVariable String postId) {
        List<Comment> comments = commentService.getCommentsForPost(postId)
        return ResponseEntity.ok(comments)
    }
}