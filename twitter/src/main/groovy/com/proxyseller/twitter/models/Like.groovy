package com.proxyseller.twitter.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "likes")
class Like {
    @Id
    String id
    String postId
    String userId
}
