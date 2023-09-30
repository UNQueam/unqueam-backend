package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.dtos.GameOutput
import com.unqueam.gamingplatform.application.dtos.GameRequest
import com.unqueam.gamingplatform.core.exceptions.Exceptions.GAME_NOT_FOUND_ERROR_MESSAGE
import com.unqueam.gamingplatform.core.mapper.GameMapper
import com.unqueam.gamingplatform.core.services.IGameService
import com.unqueam.gamingplatform.core.services.ITrackingService
import com.unqueam.gamingplatform.core.tracking.TrackingEntity
import com.unqueam.gamingplatform.infrastructure.persistence.GameAndViewsRow
import com.unqueam.gamingplatform.infrastructure.persistence.GameRepository
import jakarta.persistence.EntityNotFoundException

class GameService : IGameService {

    private val gameRepository: GameRepository
    private val gameMapper: GameMapper
    private val trackingService: ITrackingService

    constructor(aGameRepository: GameRepository, aGameMapper: GameMapper, trackingService: ITrackingService) {
        this.gameRepository = aGameRepository
        this.gameMapper = aGameMapper
        this.trackingService = trackingService
    }

    override fun publishGame(gameRequest: GameRequest) {
        val game = gameMapper.mapToInput(gameRequest)
        gameRepository.save(game)
    }

    override fun fetchGames(): List<GameOutput> {
        return gameRepository.findAll().map { gameMapper.mapToOutput(it) }
    }

    override fun fetchGameById(id: Long): GameOutput {
        val gameAndViewsRow: GameAndViewsRow = gameRepository
            .findGameAndCountViews(id)
            .orElseThrow { EntityNotFoundException(GAME_NOT_FOUND_ERROR_MESSAGE.format(id)) }

        trackingService.trackViewEvent(TrackingEntity.GAME, gameAndViewsRow.game.id!!)

        val gameViewsEvents: Long = gameAndViewsRow.views + 1

        val gameHasChanged: Boolean = gameAndViewsRow.game.checkAndUpdateRankIfMeetsTheRequirements(gameViewsEvents)

        if (gameHasChanged) {
            return gameMapper.mapToOutput(gameRepository.save(gameAndViewsRow.game))
        }
        return gameMapper.mapToOutput(gameAndViewsRow.game)
    }

    override fun deleteGameById(id: Long) {
        gameRepository.deleteById(id)
    }

    override fun updateGameById(id: Long, updatedGameRequest: GameRequest) {
        val updatedGameFromRequest = gameMapper.mapToInput(updatedGameRequest)

        val storedGame = gameRepository
            .findById(id)
            .orElseThrow { EntityNotFoundException(GAME_NOT_FOUND_ERROR_MESSAGE.format(id)) }

        val updatedGame = storedGame.syncWith(updatedGameFromRequest)

        gameRepository.save(updatedGame)
    }

}