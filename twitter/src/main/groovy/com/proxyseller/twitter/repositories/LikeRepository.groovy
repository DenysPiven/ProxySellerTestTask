package com.proxyseller.twitter.repositories

import com.proxyseller.twitter.models.Like
import org.springframework.data.mongodb.repository.MongoRepository

interface LikeRepository extends MongoRepository<Like, String> {
    List<Like> findByPostId(String postId)
    Like findByPostIdAndUserId(String postId, String userId)
}
