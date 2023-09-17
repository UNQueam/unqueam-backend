package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.auth.CustomUserDetails
import com.unqueam.gamingplatform.application.auth.JwtService
import com.unqueam.gamingplatform.application.dtos.AuthenticationOutput
import com.unqueam.gamingplatform.application.dtos.SignInRequest
import com.unqueam.gamingplatform.application.dtos.SignUpRequest
import com.unqueam.gamingplatform.core.domain.User
import com.unqueam.gamingplatform.core.mapper.AuthMapper
import com.unqueam.gamingplatform.core.services.IAuthenticationService
import com.unqueam.gamingplatform.core.services.IUserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder

private const val SIGN_IN_ERROR_MESSAGE = "El usuario y/o contraseña son incorrectos."

class AuthService : IAuthenticationService {

    private val userService: IUserService
    private val authMapper: AuthMapper
    private val jwtService: JwtService
    private val passwordEncoder: PasswordEncoder

    constructor(userService: IUserService, authMapper: AuthMapper, jwtService: JwtService, authenticationManager: AuthenticationManager, passwordEncoder: PasswordEncoder) {
        this.userService = userService
        this.authMapper = authMapper
        this.jwtService = jwtService
        this.passwordEncoder = passwordEncoder
    }

    override fun signUp(request: SignUpRequest): AuthenticationOutput {
        // TODO: Validations

        val user: User = authMapper.mapToInput(request)
        userService.save(user)
        val authToken = generateAuthToken(user)

        return authMapper.mapToOutput(user, authToken)
    }

    override fun signIn(request: SignInRequest, httpRequest: HttpServletRequest): AuthenticationOutput {
        try {
            val user: User = userService.findUserByUsername(request.username)

            if (passwordEncoder.matches(request.password, user.getPassword())) {
                val authToken = generateAuthToken(user)
                return authMapper.mapToOutput(user, authToken)
            }
            throw BadCredentialsException(SIGN_IN_ERROR_MESSAGE)
        } catch (usernameNotFoundException: UsernameNotFoundException) {
            throw BadCredentialsException(SIGN_IN_ERROR_MESSAGE)
        }
    }

    private fun generateAuthToken(user: User): String {
        return jwtService.generateToken(CustomUserDetails(user))
    }
}