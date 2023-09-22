package com.unqueam.gamingplatform.core.services

import com.unqueam.gamingplatform.application.dtos.GameOutput
import com.unqueam.gamingplatform.application.dtos.GameRequest

interface IGameService {

    fun publishGame(gameRequest: GameRequest)
    fun fetchGames(): List<GameOutput>
    fun fetchGameById(id: Long): GameOutput
    fun deleteGameById(id: Long)
    fun updateGameById(id: Long, updatedGameRequest: GameRequest)
}