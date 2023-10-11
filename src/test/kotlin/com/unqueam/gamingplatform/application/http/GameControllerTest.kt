package com.unqueam.gamingplatform.application.http

import com.unqueam.gamingplatform.application.auth.AuthContextHelper
import com.unqueam.gamingplatform.core.domain.Game
import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.core.services.implementation.GameService
import com.unqueam.gamingplatform.utils.GameRequestTestResource
import com.unqueam.gamingplatform.utils.GameTestResource
import com.unqueam.gamingplatform.utils.UserTestResource
import io.mockk.every
import io.mockk.mockkObject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import java.util.*

class GameControllerTest {

    private lateinit var gameService: GameService
    private lateinit var gameController: GameController
    private lateinit var publisher: PlatformUser

    @BeforeEach
    fun setup() {
        gameService = mock(GameService::class.java)
        gameController = GameController(gameService)
        publisher = UserTestResource.buildUser()

        mockkObject(AuthContextHelper)
        every { AuthContextHelper.getAuthenticatedUser() } returns publisher
    }

    @Test
    fun `can publish a game`() {
        val gameRequest = GameRequestTestResource.buildGameRequest()

        `when`(gameService.publishGame(gameRequest, publisher)).then { }

        val response = gameController.publishGame(gameRequest)

        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        verify(gameService).publishGame(gameRequest, publisher)
    }

    @Test
    fun `should fetch games`() {
        `when`(gameService.fetchGames(Optional.of("username"))).thenReturn(listOf())
        val response = gameController.fetchGames("username")

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(listOf<Game>())
        verify(gameService).fetchGames(Optional.of("username"))
    }

    @Test
    fun `should fetch game by id`() {
        val id = 1L
        val game = GameTestResource.buildGameOutputWithId(id)
        `when`(gameService.fetchGameById(id)).thenReturn(game)
        val response = gameController.fetchGameById(id)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(game)
        verify(gameService).fetchGameById(id)
    }

    @Test
    fun `should delete a game by id`() {
        val id = 1L
        `when`(gameService.deleteGameById(id)).then { }
        val response = gameController.deleteGameById(id)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        verify(gameService).deleteGameById(id)
    }

    @Test
    fun `should update a game by id`() {
        val id = 1L
        val gameRequest = GameRequestTestResource.buildGameRequest()

        `when`(gameService.updateGameById(id, gameRequest, publisher)).then { }
        val response = gameController.updateGameById(id, gameRequest)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        verify(gameService).updateGameById(id, gameRequest, publisher)
    }
}