package com.unqueam.gamingplatform.infrastructure.configuration

import com.unqueam.gamingplatform.infrastructure.configuration.jwt.JwtService
import com.unqueam.gamingplatform.core.helper.IPasswordFormatValidator
import com.unqueam.gamingplatform.core.mapper.AuthMapper
import com.unqueam.gamingplatform.core.mapper.GameMapper
import com.unqueam.gamingplatform.core.mapper.RequestToBeDeveloperMapper
import com.unqueam.gamingplatform.core.services.*
import com.unqueam.gamingplatform.core.services.implementation.*
import com.unqueam.gamingplatform.infrastructure.persistence.GameRepository
import com.unqueam.gamingplatform.infrastructure.persistence.RequestToBeDeveloperRepository
import com.unqueam.gamingplatform.infrastructure.persistence.TrackingEventsRepository
import com.unqueam.gamingplatform.infrastructure.persistence.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class ServicesBeans {

    @Bean
    fun gameService(gameRepository: GameRepository, gameMapper: GameMapper, trackingService: ITrackingService) : IGameService {
        return GameService(gameRepository, gameMapper, trackingService)
    }

    @Bean
    fun trackingService(trackingEventsRepository: TrackingEventsRepository) : ITrackingService {
        return TrackingService(trackingEventsRepository)
    }

    @Bean
    fun userService(userRepository: UserRepository) : IUserService {
        return UserService(userRepository)
    }

    @Bean
    fun authenticationService(
        userService: IUserService,
        authMapper: AuthMapper,
        jwtService: JwtService,
        authenticationManager: AuthenticationManager,
        passwordEncoder: PasswordEncoder,
        passwordFormatValidator: IPasswordFormatValidator) : IAuthenticationService {
        return AuthService(userService, authMapper, jwtService, authenticationManager, passwordEncoder, passwordFormatValidator)
    }

    @Bean
    fun developerService(requestToBeDeveloperRepository: RequestToBeDeveloperRepository, mapper: RequestToBeDeveloperMapper): IDeveloperService {
        return DeveloperService(requestToBeDeveloperRepository, mapper)
    }
}