package com.proxyseller.twitter.service

import com.proxyseller.twitter.model.User
import com.proxyseller.twitter.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class AuthServiceTest extends Specification {

    AuthService authService
    UserService userService = Mock()
    UserRepository userRepository = Mock()
    PasswordEncoder passwordEncoder = Mock()

    def setup() {
        authService = new AuthService()
        authService.userService = userService
        authService.userRepository = userRepository
        authService.passwordEncoder = passwordEncoder
    }

    def "test register user"() {
        given:
        User user = new User(username: "testuser", password: "password", email: "testuser@example.com")
        userRepository.findByUsername("testuser") >> Optional.empty()
        userRepository.findByEmail("testuser@example.com") >> Optional.empty()
        passwordEncoder.encode("password") >> "hashed_password"
        user.password = "hashed_password"
        userService.createUser(user) >> user

        when:
        User createdUser = authService.register(user)

        then:
        createdUser.username == "testuser"
        createdUser.password == "hashed_password"
        createdUser.email == "testuser@example.com"
    }

    def "test register user with existing username"() {
        given:
        User user = new User(username: "existinguser", password: "password", email: "testuser@example.com")
        userRepository.findByUsername("existinguser") >> Optional.of(user)

        when:
        authService.register(user)

        then:
        thrown(RuntimeException)
    }

    def "test register user with existing email"() {
        given:
        User user = new User(username: "testuser", password: "password", email: "existing@example.com")
        userRepository.findByEmail("existing@example.com") >> Optional.of(user)

        when:
        authService.register(user)

        then:
        thrown(RuntimeException)
    }

    def "test login user"() {
        given:
        User user = new User(username: "testuser", password: "hashed_password", email: "testuser@example.com")
        userRepository.findByUsername("testuser") >> Optional.of(user)
        passwordEncoder.matches("password", "hashed_password") >> true

        when:
        String token = authService.login("testuser", "password")

        then:
        token != null
    }

    def "test login with invalid credentials"() {
        given:
        User user = new User(username: "testuser", password: "hashed_password", email: "testuser@example.com")
        userRepository.findByUsername("testuser") >> Optional.of(user)
        passwordEncoder.matches("invalidpassword", "hashed_password") >> false

        when:
        authService.login("testuser", "invalidpassword")

        then:
        thrown(RuntimeException)
    }
}
