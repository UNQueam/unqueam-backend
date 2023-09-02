package com.unqueam.gamingplatform.infrastructure.configuration

import com.unqueam.gamingplatform.core.mapper.GameMapper
import com.unqueam.gamingplatform.core.services.IGameService
import com.unqueam.gamingplatform.core.services.implementation.GameService
import com.unqueam.gamingplatform.infrastructure.persistence.GameRepository
import com.unqueam.gamingplatform.infrastructure.persistence.TrackingEventsRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServicesBeans {

    @Bean
    fun gameService(gameRepository: GameRepository, gameMapper: GameMapper, trackingEventsRepository: TrackingEventsRepository) : IGameService {
        return GameService(gameRepository, gameMapper, trackingEventsRepository)
    }

}