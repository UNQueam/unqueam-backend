package com.unqueam.gamingplatform.application.http

import com.unqueam.gamingplatform.application.auth.AuthContextHelper
import com.unqueam.gamingplatform.core.domain.Game
import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.core.services.IGameService
import com.unqueam.gamingplatform.resources.InMemoryGameService
import com.unqueam.gamingplatform.utils.GameRequestTestResource
import com.unqueam.gamingplatform.utils.UserTestResource
import io.mockk.every
import io.mockk.mockkObject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class GameControllerTest {

    private lateinit var gameService: IGameService
    private lateinit var gameController: GameController
    private lateinit var publisher: PlatformUser
    private lateinit var getHiddenGamesParam: GetHiddenGamesParam

    @BeforeEach
    fun setup() {
        gameService = InMemoryGameService()
        gameController = GameController(gameService)
        publisher = UserTestResource.buildUser()
        getHiddenGamesParam = GetHiddenGamesParam(true)

        mockkObject(AuthContextHelper)
        every { AuthContextHelper.getAuthenticatedUser() } returns publisher
    }

    @Test
    fun `can publish a game`() {
        val gameRequest = GameRequestTestResource.buildGameRequest()

        val response = gameController.publishGame(gameRequest)

        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
    }

    @Test
    fun `should fetch games`() {
        val response = gameController.fetchGames("username", true)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(listOf<Game>())
    }

    @Test
    fun `should fetch game by alias`() {
        val id = "alias"
        val game = GameRequestTestResource.buildGameRequest()

        gameController.publishGame(game)

        val response = gameController.fetchGameByAlias(id)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body?.name).isEqualTo(game.name)
    }

    @Test
    fun `should delete a game by id`() {
        val id = 1L
        val response = gameController.deleteGameById(id)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `should update a game by id`() {
        val id = 1L
        val gameRequest = GameRequestTestResource.buildGameRequest()

        gameController.publishGame(gameRequest)

        val response = gameController.updateGameById(id, gameRequest)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }
}