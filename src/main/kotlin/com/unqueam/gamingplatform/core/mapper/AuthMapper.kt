package com.unqueam.gamingplatform.core.mapper

import com.unqueam.gamingplatform.application.dtos.AuthenticationOutput
import com.unqueam.gamingplatform.application.dtos.SignUpRequest
import com.unqueam.gamingplatform.core.domain.Role
import com.unqueam.gamingplatform.core.domain.PlatformUser
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

class AuthMapper(private val passwordEncoder: PasswordEncoder) {

    fun mapToInput(signUpRequest: SignUpRequest): PlatformUser {
        val encrypedPassword = passwordEncoder.encode(signUpRequest.password)
        return PlatformUser(null, signUpRequest.username, encrypedPassword, signUpRequest.email, Role.USER)
    }

    fun mapToOutput(platformUser: PlatformUser, authToken: String): AuthenticationOutput {
        return AuthenticationOutput(
            platformUser.id!!,
            platformUser.getUsername(),
            platformUser.getProfile().imageId,
            platformUser.getRole(),
            authToken,
            LocalDateTime.now()
        )
    }
}