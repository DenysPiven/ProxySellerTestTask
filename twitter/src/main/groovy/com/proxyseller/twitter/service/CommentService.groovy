package com.proxyseller.twitter.service

import com.proxyseller.twitter.model.Comment
import com.proxyseller.twitter.model.Post
import com.proxyseller.twitter.repository.CommentRepository
import com.proxyseller.twitter.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommentService {

    CommentRepository commentRepository
    PostRepository postRepository

    @Autowired
    CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository
        this.postRepository = postRepository
    }

    Comment createComment(Comment comment) {
        if (comment.postId == null || comment.postId.isEmpty()) {
            throw new IllegalArgumentException("Comment must have a postId")
        }
        if (comment.userId == null || comment.userId.isEmpty()) {
            throw new IllegalArgumentException("Comment must have a userId")
        }
        Comment savedComment = commentRepository.save(comment)
        Post post = postRepository.findById(comment.postId).orElseThrow { new RuntimeException("Post not found") }
        post.comments.add(savedComment.id)
        postRepository.save(post)
        return savedComment
    }

    Comment findCommentById(String id) {
        return commentRepository.findById(id).orElseThrow { new RuntimeException("Comment not found") }
    }

    Comment updateComment(String id, Comment updatedComment) {
        Optional<Comment> optionalComment = commentRepository.findById(id)
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get()
            comment.content = updatedComment.content
            commentRepository.save(comment)
            return comment
        } else {
            throw new RuntimeException("Comment not found")
        }
    }

    void deleteComment(String id) {
        Comment comment = commentRepository.findById(id).orElseThrow { new RuntimeException("Comment not found") }
        Post post = postRepository.findById(comment.postId).orElseThrow { new RuntimeException("Post not found") }
        post.comments.remove(id)
        postRepository.save(post)
        commentRepository.deleteById(id)
    }

    List<Comment> getCommentsForPost(String postId) {
        return commentRepository.findByPostId(postId)
    }
}