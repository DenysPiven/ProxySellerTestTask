package com.proxyseller.twitter.service

import com.proxyseller.twitter.model.User
import com.proxyseller.twitter.repository.UserRepository
import spock.lang.Specification
import org.springframework.security.crypto.password.PasswordEncoder

class UserServiceTest extends Specification {

    UserService userService
    UserRepository userRepository = Mock()
    PasswordEncoder passwordEncoder = Mock()

    def setup() {
        userService = new UserService()
        userService.userRepository = userRepository
        userService.passwordEncoder = passwordEncoder
    }

    def "test create user with hashed password"() {
        given:
        User user = new User(username: "testuser", password: "password", email: "testuser@example.com")
        passwordEncoder.encode("password") >> "hashed_password"

        when:
        userService.createUser(user)

        then:
        1 * userRepository.save({ it.password == "hashed_password" })
    }

    def "test find user by id"() {
        given:
        String id = "1"
        User user = new User(id: id, username: "testuser", password: "password")
        userRepository.findById(id) >> Optional.of(user)

        when:
        User foundUser = userService.findUserById(id)

        then:
        foundUser == user
    }

    def "test update user"() {
        given:
        String id = "1"
        User updatedUser = new User(username: "updateduser", password: "newpassword")
        User existingUser = new User(id: id, username: "testuser", password: "password")
        userRepository.findById(id) >> Optional.of(existingUser)
        passwordEncoder.encode("newpassword") >> "hashed_newpassword"

        when:
        userService.updateUser(id, updatedUser)

        then:
        1 * userRepository.save(existingUser)
        existingUser.username == "updateduser"
        existingUser.password == "hashed_newpassword"
    }

    def "test delete user"() {
        given:
        String id = "1"

        when:
        userService.deleteUser(id)

        then:
        1 * userRepository.deleteById(id)
    }

    def "test follow user"() {
        given:
        String userId = "1"
        String followUserId = "2"
        User user = new User(id: userId, username: "testuser")
        userRepository.findById(userId) >> Optional.of(user)

        when:
        userService.followUser(userId, followUserId)

        then:
        user.following.contains(followUserId)
        1 * userRepository.save(user)
    }

    def "test unfollow user"() {
        given:
        String userId = "1"
        String unfollowUserId = "2"
        User user = new User(id: userId, username: "testuser", following: [unfollowUserId])
        userRepository.findById(userId) >> Optional.of(user)

        when:
        userService.unfollowUser(userId, unfollowUserId)

        then:
        !user.following.contains(unfollowUserId)
        1 * userRepository.save(user)
    }
}