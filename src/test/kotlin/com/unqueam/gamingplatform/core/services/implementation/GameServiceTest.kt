package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.http.GetHiddenGamesParam
import com.unqueam.gamingplatform.core.domain.Game
import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.core.mapper.GameMapper
import com.unqueam.gamingplatform.core.tracking.TrackingEntity
import com.unqueam.gamingplatform.infrastructure.persistence.GameAndViewsRow
import com.unqueam.gamingplatform.infrastructure.persistence.GameRepository
import com.unqueam.gamingplatform.utils.GameRequestTestResource
import com.unqueam.gamingplatform.utils.GameTestResource
import com.unqueam.gamingplatform.utils.UserTestResource
import jakarta.persistence.EntityNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import java.util.*

class GameServiceTest {

    private lateinit var gameService: GameService
    private lateinit var gameRepository: GameRepository
    private lateinit var trackingService: TrackingService
    private lateinit var publisher: PlatformUser

    @BeforeEach
    fun setup() {
        gameRepository = mock(GameRepository::class.java)
        trackingService = mock(TrackingService::class.java)
        gameService = GameService(gameRepository, GameMapper(), trackingService)
        publisher = UserTestResource.buildUser()
    }

    @Test
    fun `should publish a game`() {
        val game = GameTestResource.buildGameWithId(1L)
        val gameRequest = GameRequestTestResource.buildGameRequest()

        val gameCaptor = ArgumentCaptor.forClass(Game::class.java)

        `when`(gameRepository.save(gameCaptor.capture())).thenReturn(game)

        gameService.publishGame(gameRequest, publisher)

        verify(gameRepository, atMostOnce()).save(gameCaptor.value)
    }

    @Test
    fun `should fetch games`() {
        val games = listOf<Game>()
        `when`(gameRepository.findAll()).thenReturn(games)

        val retrievedGames = gameService.fetchGames(Optional.empty(), GetHiddenGamesParam(true))

        assertThat(retrievedGames).isEqualTo(games)
    }

    @Test
    fun `should fetch a game by id`() {
        val id = 1L
        val gameAndView = GameAndViewsRow(GameTestResource.buildGameWithId(id), 0)
        val optionalGame = Optional.of(gameAndView)
        `when`(gameRepository.findGameAndCountViews(id)).thenReturn(optionalGame)

        val retrievedGame = gameService.fetchGameById(id)
        assertThat(retrievedGame.id).isEqualTo(gameAndView.game.id)
        assertThat(0).isEqualTo(gameAndView.views)
        verify(trackingService, atMostOnce()).trackViewEvent(TrackingEntity.GAME, id)
    }

    @Test
    fun `should fetch an exception when game was not found`() {
        val id = 1L
        `when`(gameRepository.findById(id)).thenThrow(EntityNotFoundException("No se encontró un juego con el ID: 1"))

        assertThatThrownBy { gameService.fetchGameById(id) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage("No se encontró un juego con el ID: 1")
    }

    @Test
    fun `should delete a game by id`() {
        val id = 1L
        `when`(gameRepository.deleteById(id)).then { }

        gameService.deleteGameById(id)
        verify(gameRepository).deleteById(id)
    }

    @Test
    fun `test update a game by id`() {
        val gameId = 1L
        val game = GameTestResource.buildGameWithId(gameId)
        val optionalGame = Optional.of(game)
        val gameRequest = GameRequestTestResource.buildGameRequest()

        `when`(gameRepository.findById(gameId)).thenReturn(optionalGame)

        gameService.updateGameById(gameId, gameRequest, publisher)

        verify(gameRepository).save(any(Game::class.java))
    }
}
