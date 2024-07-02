package com.proxyseller.twitter.repositories

import com.proxyseller.twitter.models.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username)
}
