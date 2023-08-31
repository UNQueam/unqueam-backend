package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.dtos.GameRequest
import com.unqueam.gamingplatform.core.domain.Game
import com.unqueam.gamingplatform.core.services.IGameService
import com.unqueam.gamingplatform.infrastructure.persistence.GameRepository
import jakarta.persistence.EntityNotFoundException

class GameService : IGameService {

    private val gameRepository: GameRepository

    constructor(aGameRepository: GameRepository) {
        this.gameRepository = aGameRepository
    }

    override fun publishGame(gameRequest: Game) {
        gameRepository.save(gameRequest)
    }

    override fun fetchGames(): List<Game> {
        return gameRepository.findAll()
    }

    override fun fetchGameById(id: Long): Game {
        return gameRepository
            .findById(id)
            .orElseThrow { EntityNotFoundException("There is no game with ID: %s".format(id)) }
    }

    override fun deleteGameById(id: Long) {
        gameRepository.deleteById(id)
    }

    override fun updateGameById(id: Long, updatedGame: GameRequest) {
        // TODO: Do implementation!
    }

}