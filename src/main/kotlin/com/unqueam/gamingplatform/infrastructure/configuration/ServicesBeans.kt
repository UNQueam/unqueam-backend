package com.unqueam.gamingplatform.infrastructure.configuration

import com.unqueam.gamingplatform.core.mapper.GameMapper
import com.unqueam.gamingplatform.core.services.IGameService
import com.unqueam.gamingplatform.core.services.ITrackingService
import com.unqueam.gamingplatform.core.services.IUserService
import com.unqueam.gamingplatform.core.services.implementation.GameService
import com.unqueam.gamingplatform.core.services.implementation.TrackingService
import com.unqueam.gamingplatform.core.services.implementation.UserService
import com.unqueam.gamingplatform.infrastructure.persistence.GameRepository
import com.unqueam.gamingplatform.infrastructure.persistence.TrackingEventsRepository
import com.unqueam.gamingplatform.infrastructure.persistence.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

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
}