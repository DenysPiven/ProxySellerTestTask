package com.proxyseller.twitter.controller

import com.proxyseller.twitter.model.User
import com.proxyseller.twitter.service.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
@Api(value = "User Controller", description = "Operations related to users")
class UserController {

    @Autowired
    UserService userService

    @PostMapping("/")
    @ApiOperation(value = "Create a user", notes = "Creates a new user account")
    ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user)
        return ResponseEntity.ok(createdUser)
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a user by ID", notes = "Retrieves a user by their ID")
    ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userService.findUserById(id)
        return ResponseEntity.ok(user)
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a user", notes = "Updates the details of an existing user")
    ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser)
        return ResponseEntity.ok(user)
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a user", notes = "Deletes the specified user")
    ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{id}/follow")
    @ApiOperation(value = "Follow a user", notes = "Follows the specified user")
    ResponseEntity<Void> followUser(@PathVariable String id, @RequestParam String followUserId) {
        userService.followUser(id, followUserId)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{id}/unfollow")
    @ApiOperation(value = "Unfollow a user", notes = "Unfollows the specified user")
    ResponseEntity<Void> unfollowUser(@PathVariable String id, @RequestParam String unfollowUserId) {
        userService.unfollowUser(id, unfollowUserId)
        return ResponseEntity.ok().build()
    }
}