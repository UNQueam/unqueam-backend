package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.auth.CustomUserDetails
import com.unqueam.gamingplatform.infrastructure.configuration.jwt.JwtService
import com.unqueam.gamingplatform.application.dtos.AuthenticationOutput
import com.unqueam.gamingplatform.application.dtos.SignInRequest
import com.unqueam.gamingplatform.application.dtos.SignUpRequest
import com.unqueam.gamingplatform.core.exceptions.Exceptions
import com.unqueam.gamingplatform.core.exceptions.SignUpFormException
import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.core.domain.UserProfile
import com.unqueam.gamingplatform.core.helper.IPasswordFormatValidator
import com.unqueam.gamingplatform.core.mapper.AuthMapper
import com.unqueam.gamingplatform.core.services.IAuthenticationService
import com.unqueam.gamingplatform.core.services.IUserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder

class AuthService : IAuthenticationService {

    private val userService: IUserService
    private val authMapper: AuthMapper
    private val jwtService: JwtService
    private val passwordEncoder: PasswordEncoder
    private val passwordFormatValidator: IPasswordFormatValidator

    constructor(userService: IUserService, authMapper: AuthMapper, jwtService: JwtService, authenticationManager: AuthenticationManager, passwordEncoder: PasswordEncoder, passwordFormatValidator: IPasswordFormatValidator) {
        this.userService = userService
        this.authMapper = authMapper
        this.jwtService = jwtService
        this.passwordEncoder = passwordEncoder
        this.passwordFormatValidator = passwordFormatValidator
    }

    override fun signUp(request: SignUpRequest): AuthenticationOutput {
        executeSignUpValidations(request)

        val platformUser: PlatformUser = authMapper.mapToInput(request)
        userService.save(platformUser)

        val authToken = generateAuthToken(platformUser)
        return authMapper.mapToOutput(platformUser, authToken)
    }

    override fun signIn(request: SignInRequest, httpRequest: HttpServletRequest): AuthenticationOutput {
        try {
            val platformUser: PlatformUser = userService.findUserByUsername(request.username)

            if (passwordDoesNotMatchWithPasswordOfRegisteredUser(request, platformUser))
                throw BadCredentialsException(Exceptions.USER_OR_PASSWORD_ARE_INCORRECT)

            val authToken = generateAuthToken(platformUser)
            return authMapper.mapToOutput(platformUser, authToken)
        } catch (usernameNotFoundException: UsernameNotFoundException) {
            throw BadCredentialsException(Exceptions.USER_OR_PASSWORD_ARE_INCORRECT)
        }
    }

    private fun passwordDoesNotMatchWithPasswordOfRegisteredUser(request: SignInRequest, platformUser: PlatformUser): Boolean {
        return !passwordEncoder.matches(request.password, platformUser.getPassword())
    }

    private fun executeSignUpValidations(request: SignUpRequest) {
        val errors: MutableMap<String, List<String>> = mutableMapOf()

        passwordFormatValidator.validateConstraints(request.password, errors)
        validateUsernameOrEmailAreNotInUse(request, errors)

        if (errors.isNotEmpty()) throw SignUpFormException(errors)
    }

    private fun validateUsernameOrEmailAreNotInUse(request: SignUpRequest, errorsMap: MutableMap<String, List<String>>) {
        userService
            .findUserByUsernameOrEmail(request.username, request.email)
            .ifPresent { fetchedUser ->
                if (stringIsEquals(fetchedUser.getUsername(), request.username.lowercase()))
                    errorsMap["username"] = listOf(Exceptions.THE_USERNAME_IS_ALREADY_IN_USE)

                if (stringIsEquals(fetchedUser.getEmail(), request.email.lowercase()))
                    errorsMap["email"] = listOf(Exceptions.THE_EMAIL_ADDRESS_IS_ALREADY_IN_USE)
            }
    }

    private fun generateAuthToken(platformUser: PlatformUser): String {
        return jwtService.generateToken(CustomUserDetails(platformUser))
    }

    private fun stringIsEquals(someString: String, otherString: String): Boolean {
        return someString.lowercase() == otherString.lowercase()
    }
}