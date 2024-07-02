package com.proxyseller.twitter.services

import com.proxyseller.twitter.models.User
import com.proxyseller.twitter.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    UserRepository userRepository

    User registerUser(User user) {
        userRepository.save(user)
    }

    User findUserByUsername(String username) {
        userRepository.findByUsername(username)
    }

    // Інші методи для редагування, видалення користувача тощо
}
