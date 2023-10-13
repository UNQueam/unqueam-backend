package com.unqueam.gamingplatform.integration_tests.data_loader

import com.unqueam.gamingplatform.application.dtos.GameRequest
import com.unqueam.gamingplatform.core.services.IGameService
import com.unqueam.gamingplatform.utils.UserTestResource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("test")
class GameDataLoader(@Autowired private val gameService: IGameService) {

    fun loadNewGame(gameRequest: GameRequest, username: String) {
        val platformUser = UserTestResource.buildUser(username)
        gameService.publishGame(gameRequest, platformUser)
    }
}