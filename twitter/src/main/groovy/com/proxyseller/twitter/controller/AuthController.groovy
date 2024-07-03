package com.proxyseller.twitter.controller

import com.proxyseller.twitter.model.User
import com.proxyseller.twitter.service.AuthService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
@Api(value = "Auth Controller", description = "Operations related to authentication")
class AuthController {

    @Autowired
    AuthService authService

    @PostMapping("/register")
    @ApiOperation(value = "Register a new user", notes = "Creates a new user account")
    ResponseEntity<User> register(@RequestBody User user) {
        try {
            User createdUser = authService.register(user)
            return ResponseEntity.ok(createdUser)
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }
    }

    @PostMapping("/login")
    @ApiOperation(value = "Login", notes = "Authenticates a user and returns a token")
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
    @ApiOperation(value = "Logout", notes = "Logs out the current user")
    ResponseEntity<Void> logout() {
        authService.logout()
        return ResponseEntity.ok().build()
    }
}
