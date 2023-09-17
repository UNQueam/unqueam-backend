package com.unqueam.gamingplatform.infrastructure.configuration

import com.unqueam.gamingplatform.application.auth.JwtService
import com.unqueam.gamingplatform.core.helper.IPasswordFormatValidator
import com.unqueam.gamingplatform.core.mapper.AuthMapper
import com.unqueam.gamingplatform.core.mapper.GameMapper
import com.unqueam.gamingplatform.core.mapper.UserMapper
import com.unqueam.gamingplatform.core.services.IAuthenticationService
import com.unqueam.gamingplatform.core.services.IGameService
import com.unqueam.gamingplatform.core.services.ITrackingService
import com.unqueam.gamingplatform.core.services.IUserService
import com.unqueam.gamingplatform.core.services.implementation.AuthService
import com.unqueam.gamingplatform.core.services.implementation.GameService
import com.unqueam.gamingplatform.core.services.implementation.TrackingService
import com.unqueam.gamingplatform.core.services.implementation.UserService
import com.unqueam.gamingplatform.infrastructure.persistence.GameRepository
import com.unqueam.gamingplatform.infrastructure.persistence.TrackingEventsRepository
import com.unqueam.gamingplatform.infrastructure.persistence.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class ServicesBeans {

    @Bean
    fun gameService(gameRepository: GameRepository, trackingService: ITrackingService) : IGameService {
        return GameService(gameRepository, GameMapper(), trackingService)
    }

    @Bean
    fun trackingService(trackingEventsRepository: TrackingEventsRepository) : ITrackingService {
        return TrackingService(trackingEventsRepository)
    }

    @Bean
    fun userService(userRepository: UserRepository) : IUserService {
        return UserService(userRepository, UserMapper())
    }

    @Bean
    fun authenticationService(
        userService: IUserService,
        jwtService: JwtService,
        authenticationManager: AuthenticationManager,
        passwordEncoder: PasswordEncoder,
        passwordFormatValidator: IPasswordFormatValidator) : IAuthenticationService {
        return AuthService(userService, AuthMapper(passwordEncoder), jwtService, authenticationManager, passwordEncoder, passwordFormatValidator)
    }
}