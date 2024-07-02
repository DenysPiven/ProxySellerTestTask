package com.proxyseller.twitter.controllers

import com.proxyseller.twitter.models.User
import com.proxyseller.twitter.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController {

    @Autowired
    UserService userService

    @PostMapping("/register")
    ResponseEntity<User> registerUser(@RequestBody User user) {
        User createdUser = userService.registerUser(user)
        ResponseEntity.ok(createdUser)
    }

    @GetMapping("/{username}")
    ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findUserByUsername(username)
        ResponseEntity.ok(user)
    }

    // Інші методи для редагування, видалення користувача тощо
}
