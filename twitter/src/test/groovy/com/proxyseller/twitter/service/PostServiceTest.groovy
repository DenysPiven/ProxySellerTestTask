package com.proxyseller.twitter.service

import com.proxyseller.twitter.model.Post
import com.proxyseller.twitter.repository.PostRepository
import spock.lang.Specification

class PostServiceTest extends Specification {

    PostRepository postRepository = Mock()
    PostService postService

    def setup() {
        postService = new PostService(postRepository)
    }

    def "test create post"() {
        given:
        Post post = new Post(id: "1", userId: "user1", content: "Hello World")

        when:
        postService.createPost(post)

        then:
        1 * postRepository.save(post)
    }

    def "test find post by id"() {
        given:
        Post post = new Post(id: "1", userId: "user1", content: "Hello World")
        postRepository.findById("1") >> Optional.of(post)

        when:
        Post result = postService.findPostById("1")

        then:
        result == post
    }

    def "test update post"() {
        given:
        Post post = new Post(id: "1", userId: "user1", content: "Hello World")
        postRepository.findById("1") >> Optional.of(post)
        Post updatedPost = new Post(content: "Updated Content")

        when:
        postService.updatePost("1", updatedPost)

        then:
        1 * postRepository.save(post)
        post.content == "Updated Content"
    }

    def "test delete post"() {
        when:
        postService.deletePost("1")

        then:
        1 * postRepository.deleteById("1")
    }

    def "test like post"() {
        given:
        Post post = new Post(id: "1", userId: "user1", content: "Hello World", likes: [])
        postRepository.findById("1") >> Optional.of(post)

        when:
        postService.likePost("1", "user1")

        then:
        1 * postRepository.save(post)
        post.likes.contains("user1")
    }

    def "test unlike post"() {
        given:
        Post post = new Post(id: "1", userId: "user1", content: "Hello World", likes: ["user1"])
        postRepository.findById("1") >> Optional.of(post)

        when:
        postService.unlikePost("1", "user1")

        then:
        1 * postRepository.save(post)
        !post.likes.contains("user1")
    }

    def "test get user feed"() {
        given:
        List<Post> posts = [new Post(id: "1", userId: "user1", content: "Hello World")]
        postRepository.findByUserIdIn(["user1"]) >> posts

        when:
        List<Post> result = postService.getUserFeed(["user1"])

        then:
        result == posts
    }

    def "test get user posts"() {
        given:
        List<Post> posts = [new Post(id: "1", userId: "user1", content: "Hello World")]
        postRepository.findByUserId("user1") >> posts

        when:
        List<Post> result = postService.getUserPosts("user1")

        then:
        result == posts
    }
}