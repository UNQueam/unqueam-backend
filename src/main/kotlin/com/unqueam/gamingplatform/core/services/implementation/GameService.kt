package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.dtos.GameRequest
import com.unqueam.gamingplatform.core.domain.Game
import com.unqueam.gamingplatform.core.mapper.GameMapper
import com.unqueam.gamingplatform.core.services.IGameService
import com.unqueam.gamingplatform.infrastructure.persistence.GameRepository
import jakarta.persistence.EntityNotFoundException

class GameService : IGameService {

    private val gameRepository: GameRepository
    private val gameMapper: GameMapper

    constructor(aGameRepository: GameRepository, aGameMapper: GameMapper) {
        this.gameRepository = aGameRepository
        this.gameMapper = aGameMapper
    }

    override fun publishGame(gameRequest: GameRequest) {
        val game = gameMapper.map(gameRequest)
        gameRepository.save(game)
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

    override fun updateGameById(id: Long, updatedGameRequest: GameRequest) {
        val updatedGameFromRequest = gameMapper.map(updatedGameRequest)

        val storedGame = fetchGameById(id)
        val updatedGame = storedGame.syncWith(updatedGameFromRequest)

        gameRepository.save(updatedGame)
    }

}