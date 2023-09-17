package com.unqueam.gamingplatform.core.mapper

import com.unqueam.gamingplatform.application.dtos.AuthenticationOutput
import com.unqueam.gamingplatform.application.dtos.SignUpRequest
import com.unqueam.gamingplatform.core.domain.Role
import com.unqueam.gamingplatform.core.domain.User
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

class AuthMapper(private val passwordEncoder: PasswordEncoder) {

    fun mapToInput(signUpRequest: SignUpRequest): User {
        val encrypedPassword = passwordEncoder.encode(signUpRequest.password)
        return User(null, signUpRequest.username, encrypedPassword, signUpRequest.email, Role.USER)
    }

    fun mapToOutput(user: User, authToken: String): AuthenticationOutput {
        return AuthenticationOutput(
            user.id!!,
            user.getUsername(),
            user.getRole(),
            authToken,
            LocalDateTime.now()
        )
    }
}