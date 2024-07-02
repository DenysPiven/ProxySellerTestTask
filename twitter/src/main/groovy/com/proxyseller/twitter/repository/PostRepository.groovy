package com.proxyseller.twitter.repository

import com.proxyseller.twitter.model.Post
import org.springframework.data.mongodb.repository.MongoRepository

interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByUserId(String userId)
    List<Post> findByUserIdIn(List<String> userIds)
}