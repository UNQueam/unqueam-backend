package com.unqueam.gamingplatform.resources

import com.unqueam.gamingplatform.application.dtos.GameOutput
import com.unqueam.gamingplatform.application.dtos.GameRequest
import com.unqueam.gamingplatform.application.http.GetHiddenGamesParam
import com.unqueam.gamingplatform.core.domain.Game
import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.core.exceptions.Exceptions.GAME_NOT_FOUND_ERROR_MESSAGE
import com.unqueam.gamingplatform.core.mapper.GameMapper
import com.unqueam.gamingplatform.core.services.IGameService
import jakarta.persistence.EntityNotFoundException
import java.util.*

class InMemoryGameService : IGameService {

    private var gamesCounter: Long = 1
    private var gameRepository: MutableList<Game> = mutableListOf()
    private val gameMapper: GameMapper = GameMapper()

    override fun publishGame(gameRequest: GameRequest, publisher: PlatformUser) {
        val game = gameMapper.mapToInput(gameRequest, publisher)
        game.id = gamesCounter
        gamesCounter++
        gameRepository.add(game)
    }

    override fun fetchGames(username: Optional<String>, getHiddenGamesParam: GetHiddenGamesParam): List<GameOutput> {
        return gameRepository.map { gameMapper.mapToOutput(it) }
    }

    override fun fetchGameById(id: Long): GameOutput {
        val game = gameRepository.find { it.id == id } ?: throw EntityNotFoundException(GAME_NOT_FOUND_ERROR_MESSAGE.format(id))
        return gameMapper.mapToOutput(game)
    }

    override fun deleteGameById(id: Long) {
        gameRepository = gameRepository.filter { it -> it.id != id }.toMutableList()
    }

    override fun updateGameById(id: Long, updatedGameRequest: GameRequest, publisher: PlatformUser) {
        val updatedGameFromRequest = gameMapper.mapToInput(updatedGameRequest, publisher)

        val storedGame = gameRepository.find { it.id == id } ?: throw EntityNotFoundException(GAME_NOT_FOUND_ERROR_MESSAGE.format(id))

        storedGame.syncWith(updatedGameFromRequest)
    }

    override fun hideGameById(id: Long, publisher: PlatformUser) {
        TODO("Not yet implemented")
    }

    override fun exposeGameById(id: Long, publisher: PlatformUser) {
        TODO("Not yet implemented")
    }

}