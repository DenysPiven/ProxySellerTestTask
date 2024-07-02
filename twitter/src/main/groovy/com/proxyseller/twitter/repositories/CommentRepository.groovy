package com.proxyseller.twitter.repositories

import com.proxyseller.twitter.models.Comment
import org.springframework.data.mongodb.repository.MongoRepository

interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPostId(String postId)
}
