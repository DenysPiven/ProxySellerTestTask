package com.proxyseller.twitter.repositories

import com.proxyseller.twitter.models.Post
import org.springframework.data.mongodb.repository.MongoRepository

interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByUserId(String userId)
}
