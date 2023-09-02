package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.dtos.GameRequest
import com.unqueam.gamingplatform.core.domain.Game
import com.unqueam.gamingplatform.core.mapper.GameMapper
import com.unqueam.gamingplatform.core.services.IGameService
import com.unqueam.gamingplatform.infrastructure.persistence.GameAndViews
import com.unqueam.gamingplatform.infrastructure.persistence.GameRepository
import com.unqueam.gamingplatform.infrastructure.persistence.TrackingEventsRepository
import jakarta.persistence.EntityNotFoundException
import java.time.LocalDateTime

class GameService : IGameService {

    private val gameRepository: GameRepository
    private val gameMapper: GameMapper
    private val trackingEventRepository: TrackingEventsRepository

    constructor(aGameRepository: GameRepository, aGameMapper: GameMapper, trackingEventRepository: TrackingEventsRepository) {
        this.gameRepository = aGameRepository
        this.gameMapper = aGameMapper
        this.trackingEventRepository = trackingEventRepository
    }

    override fun publishGame(gameRequest: GameRequest) {
        val game = gameMapper.map(gameRequest)
        gameRepository.save(game)
        trackingEventRepository.save(TrackingEvent(null, TrackingType.VIEW, TrackingEntity.GAME, game.id!!, LocalDateTime.now()))
    }

    override fun fetchGames(): List<Game> {
        return gameRepository.findAll()
    }

    override fun fetchGameById(id: Long): Game {
        // GameAndViews en misma query haciendo join por entity
        val gameAndViews: GameAndViews = gameRepository
            .findGameAndCountViews(id)
            .orElseThrow { EntityNotFoundException("There is no game with ID: %s".format(id)) }

        trackingEventRepository.save(TrackingEvent(null, TrackingType.VIEW, TrackingEntity.GAME, gameAndViews.game.id!!, LocalDateTime.now()))

        val gameViewsEvents: Long = gameAndViews.views + 1

        val wasUpdated: Boolean = gameAndViews.game.checkAndUpdateRankIfMeetsTheRequirements(gameViewsEvents)

        if (wasUpdated) {
            return gameRepository.save(gameAndViews.game)
        }
        return gameAndViews.game
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