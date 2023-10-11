package com.unqueam.gamingplatform.core.services

import com.unqueam.gamingplatform.application.dtos.GameOutput
import com.unqueam.gamingplatform.application.dtos.GameRequest
import com.unqueam.gamingplatform.core.domain.PlatformUser
import java.util.*

interface IGameService {

    fun publishGame(gameRequest: GameRequest, publisher: PlatformUser)
    fun fetchGames(username: Optional<String>): List<GameOutput>
    fun fetchGameById(id: Long): GameOutput
    fun deleteGameById(id: Long)
    fun updateGameById(id: Long, updatedGameRequest: GameRequest, publisher: PlatformUser)
}