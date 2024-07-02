package com.proxyseller.twitter.service

import com.proxyseller.twitter.model.User
import com.proxyseller.twitter.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    UserRepository userRepository

    User createUser(User user) {
        return userRepository.save(user)
    }

    User findUserById(String id) {
        Optional<User> userOptional = userRepository.findById(id)
        return userOptional.orElseThrow { new RuntimeException("User not found") }
    }

    User updateUser(String id, User updatedUser) {
        User user = userRepository.findById(id).orElseThrow { new RuntimeException("User not found") }
        user.username = updatedUser.username
        user.password = updatedUser.password
        return userRepository.save(user)
    }

    void deleteUser(String id) {
        userRepository.deleteById(id)
    }

    void followUser(String id, String followUserId) {
        User user = userRepository.findById(id).orElseThrow { new RuntimeException("User not found") }
        user.following.add(followUserId)
        userRepository.save(user)
    }

    void unfollowUser(String id, String unfollowUserId) {
        User user = userRepository.findById(id).orElseThrow { new RuntimeException("User not found") }
        user.following.remove(unfollowUserId)
        userRepository.save(user)
    }
}