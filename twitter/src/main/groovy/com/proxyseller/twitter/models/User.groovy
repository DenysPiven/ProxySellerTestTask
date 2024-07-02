package com.proxyseller.twitter.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
class User {
    @Id
    String id
    String username
    String password
    String email
    List<String> following = []
}
