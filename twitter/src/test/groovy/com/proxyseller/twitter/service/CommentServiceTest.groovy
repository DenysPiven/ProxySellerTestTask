package com.proxyseller.twitter.service

import com.proxyseller.twitter.model.Comment
import com.proxyseller.twitter.model.Post
import com.proxyseller.twitter.repository.CommentRepository
import com.proxyseller.twitter.repository.PostRepository
import spock.lang.Specification

class CommentServiceTest extends Specification {

    CommentRepository commentRepository = Mock()
    PostRepository postRepository = Mock()
    CommentService commentService

    def setup() {
        commentService = new CommentService(commentRepository, postRepository)
    }

    def "test create comment"() {
        given:
        Comment comment = new Comment(id: "1", postId: "post1", userId: "user1", content: "This is a test comment")
        Post post = new Post(id: "post1", userId: "user1", content: "Hello World", comments: [])

        when:
        commentService.createComment(comment)

        then:
        1 * commentRepository.save(comment) >> comment
        1 * postRepository.findById("post1") >> Optional.of(post)
        1 * postRepository.save(post)
        post.comments.contains(comment.id)
    }

    def "test find comment by id"() {
        given:
        String id = "1"
        Comment comment = new Comment(id: id, postId: "post1", userId: "user1", content: "This is a test comment")
        commentRepository.findById(id) >> Optional.of(comment)

        when:
        Comment result = commentService.findCommentById(id)

        then:
        result == comment
    }

    def "test update comment"() {
        given:
        String id = "1"
        Comment existingComment = new Comment(id: id, postId: "post1", userId: "user1", content: "This is a test comment")
        Comment updatedComment = new Comment(content: "Updated content")
        commentRepository.findById(id) >> Optional.of(existingComment)
        commentRepository.save(existingComment) >> existingComment

        when:
        Comment result = commentService.updateComment(id, updatedComment)

        then:
        1 * commentRepository.save(existingComment)
        result.content == "Updated content"
    }

    def "test delete comment"() {
        given:
        String id = "1"
        Comment comment = new Comment(id: id, postId: "post1", userId: "user1", content: "This is a test comment")
        Post post = new Post(id: "post1", userId: "user1", content: "Hello World", comments: [comment.id])
        commentRepository.findById(id) >> Optional.of(comment)
        postRepository.findById("post1") >> Optional.of(post)

        when:
        commentService.deleteComment(id)

        then:
        1 * commentRepository.deleteById(id)
        1 * postRepository.save(post)
        !post.comments.contains(comment.id)
    }

    def "test get comments for post"() {
        given:
        List<Comment> comments = [new Comment(postId: "post1", userId: "user1", content: "Comment 1")]
        commentRepository.findByPostId("post1") >> comments

        when:
        List<Comment> result = commentService.getCommentsForPost("post1")

        then:
        result == comments
    }
}