package com.proxyseller.twitter.controller

import com.proxyseller.twitter.model.User
import com.proxyseller.twitter.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController {

    @Autowired
    AuthService authService

    @PostMapping("/register")
    ResponseEntity<User> register(@RequestBody User user) {
        try {
            User createdUser = authService.register(user)
            return ResponseEntity.ok(createdUser)
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }
    }

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        try {
            String username = credentials.get("username")
            String password = credentials.get("password")
            String token = authService.login(username, password)
            return ResponseEntity.ok(token)
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials")
        }
    }

    @PostMapping("/logout")
    ResponseEntity<Void> logout() {
        authService.logout()
        return ResponseEntity.ok().build()
    }
}
