package com.unqueam.gamingplatform.application.auth

import com.unqueam.gamingplatform.application.dtos.SignUpRequest
import com.unqueam.gamingplatform.application.dtos.SigninRequest
import com.unqueam.gamingplatform.core.domain.Role
import com.unqueam.gamingplatform.core.domain.User
import com.unqueam.gamingplatform.infrastructure.persistence.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*


@Service
class AuthenticationServiceImpl : AuthenticationService {
    private val userRepository: UserRepository
    private val passwordEncoder: PasswordEncoder
    private val jwtService: JwtService
    private val authenticationManager: AuthenticationManager

    constructor(userRepository: UserRepository, passwordEncoder: PasswordEncoder, jwtService: JwtService, authenticationManager: AuthenticationManager) {
        this.userRepository = userRepository
        this.passwordEncoder = passwordEncoder
        this.jwtService = jwtService
        this.authenticationManager = authenticationManager
    }

    override fun signup(request: SignUpRequest): String {
        val user: User = User(null, request.username, passwordEncoder.encode(request.password), request.email, Role.USER)
        userRepository.save<User>(user)
        return jwtService.generateToken(CustomUserDetails(user))
    }

    override fun signin(request: SigninRequest): String {
        authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(request.username, request.password))
        val user = Optional.ofNullable(userRepository.findByUsername(request.username))
                .orElseThrow { IllegalArgumentException("Invalid email or password") }
        return jwtService.generateToken(CustomUserDetails(user))
    }
}