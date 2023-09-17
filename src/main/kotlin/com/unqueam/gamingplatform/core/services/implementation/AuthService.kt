package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.auth.CustomUserDetails
import com.unqueam.gamingplatform.application.auth.JwtService
import com.unqueam.gamingplatform.application.dtos.AuthenticationOutput
import com.unqueam.gamingplatform.application.dtos.SignInRequest
import com.unqueam.gamingplatform.application.dtos.SignUpRequest
import com.unqueam.gamingplatform.core.domain.User
import com.unqueam.gamingplatform.core.mapper.AuthMapper
import com.unqueam.gamingplatform.core.services.IAuthenticationService
import com.unqueam.gamingplatform.infrastructure.persistence.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import java.util.Optional

class AuthService : IAuthenticationService {

    private val userRepository: UserRepository
    private val authMapper: AuthMapper
    private val jwtService: JwtService
    private val authenticationManager: AuthenticationManager

    constructor(userRepository: UserRepository, authMapper: AuthMapper, jwtService: JwtService, authenticationManager: AuthenticationManager) {
        this.userRepository = userRepository
        this.authMapper = authMapper
        this.jwtService = jwtService
        this.authenticationManager = authenticationManager
    }

    override fun signUp(request: SignUpRequest): AuthenticationOutput {
        // TODO: Validations

        val user: User = authMapper.mapToInput(request)
        userRepository.save<User>(user)
        val authToken = generateAuthToken(user)

        return authMapper.mapToOutput(user, authToken)
    }

    override fun signIn(request: SignInRequest): AuthenticationOutput {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.username, request.password))
        val user = Optional.ofNullable(userRepository.findByUsername(request.username))
                .orElseThrow { IllegalArgumentException("Invalid email or password") }
        val authToken = generateAuthToken(user)

        return authMapper.mapToOutput(user, authToken)
    }

    private fun generateAuthToken(user: User): String {
        return jwtService.generateToken(CustomUserDetails(user))
    }
}