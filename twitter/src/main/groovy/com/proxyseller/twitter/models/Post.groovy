package com.proxyseller.twitter.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "posts")
class Post {
    @Id
    String id
    String userId
    String content
    Date createdAt = new Date()
    List<String> likes = []
    List<Comment> comments = []
}
