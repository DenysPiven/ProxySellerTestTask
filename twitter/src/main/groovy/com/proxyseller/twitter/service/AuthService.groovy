package com.proxyseller.twitter.service

import com.proxyseller.twitter.model.User
import com.proxyseller.twitter.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService {

    @Autowired
    UserService userService

    @Autowired
    UserRepository userRepository

    @Autowired
    PasswordEncoder passwordEncoder

    User register(User user) {
        if (userRepository.findByUsername(user.username).isPresent()) {
            throw new RuntimeException("Username is already taken")
        }
        if (userRepository.findByEmail(user.email).isPresent()) {
            throw new RuntimeException("Email is already in use")
        }
        return userService.createUser(user)
    }

    String login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow { new RuntimeException("Invalid credentials") }
        if (passwordEncoder.matches(password, user.password)) {
            return Base64.getEncoder().encodeToString((username + ":" + password).getBytes())
        } else {
            throw new RuntimeException("Invalid credentials")
        }
    }

    void logout() {
        // Logout logic (for stateless APIs, usually client just deletes the token)
    }
}
