package com.unqueam.gamingplatform.integration_tests.data_loader

import com.unqueam.gamingplatform.application.dtos.GameRequest
import com.unqueam.gamingplatform.core.domain.Game
import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.core.services.IGameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("test")
class GameDataLoader(@Autowired private val gameService: IGameService) {

    fun loadNewGame(gameRequest: GameRequest, user: PlatformUser): Game {
        return gameService.publishGame(gameRequest, user)
    }
}