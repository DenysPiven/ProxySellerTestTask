package com.proxyseller.twitter.service

import com.proxyseller.twitter.model.Post
import com.proxyseller.twitter.model.User
import com.proxyseller.twitter.repository.PostRepository
import com.proxyseller.twitter.repository.UserRepository
import spock.lang.Specification

class PostServiceTest extends Specification {

    PostRepository postRepository = Mock()
    UserRepository userRepository = Mock()
    PostService postService

    def setup() {
        postService = new PostService()
        postService.postRepository = postRepository
        postService.userRepository = userRepository
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
        String userId = "1"
        User user = new User(id: userId, following: ["2", "3"])
        List<Post> posts = [
                new Post(id: "1", userId: "2", content: "Post from user 2"),
                new Post(id: "2", userId: "3", content: "Post from user 3")
        ]

        when:
        userRepository.findById(userId) >> Optional.of(user)
        postRepository.findByUserIdIn(user.following) >> posts

        List<Post> result = postService.getUserFeed(userId)

        then:
        result.size() == 2
        result == posts
    }

    def "test get user posts"() {
        given:
        String userId = "1"
        List<Post> posts = [
                new Post(id: "1", userId: userId, content: "User's post 1"),
                new Post(id: "2", userId: userId, content: "User's post 2")
        ]

        when:
        postRepository.findByUserId(userId) >> posts

        List<Post> result = postService.getUserPosts(userId)

        then:
        result.size() == 2
        result == posts
    }
}