package com.proxyseller.twitter.controller

import com.proxyseller.twitter.model.User
import com.proxyseller.twitter.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController {

    @Autowired
    UserService userService

    @PostMapping("/")
    ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user)
        return ResponseEntity.ok(createdUser)
    }

    @GetMapping("/{id}")
    ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userService.findUserById(id)
        return ResponseEntity.ok(user)
    }

    @PutMapping("/{id}")
    ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser)
        return ResponseEntity.ok(user)
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{id}/follow")
    ResponseEntity<Void> followUser(@PathVariable String id, @RequestParam String followUserId) {
        userService.followUser(id, followUserId)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{id}/unfollow")
    ResponseEntity<Void> unfollowUser(@PathVariable String id, @RequestParam String unfollowUserId) {
        userService.unfollowUser(id, unfollowUserId)
        return ResponseEntity.ok().build()
    }
}