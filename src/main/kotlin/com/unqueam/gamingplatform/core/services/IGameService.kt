package com.unqueam.gamingplatform.core.services

import com.unqueam.gamingplatform.application.dtos.GameRequest
import com.unqueam.gamingplatform.core.domain.Game

interface IGameService {

    fun publishGame(gameRequest: Game)
    fun fetchGames(): List<Game>
    fun fetchGameById(id: Long): Game
    fun deleteGameById(id: Long)
    fun updateGameById(id: Long, updatedGame: GameRequest)
}