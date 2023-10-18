package com.unqueam.gamingplatform.integration_tests

import com.unqueam.gamingplatform.application.http.API
import com.unqueam.gamingplatform.integration_tests.data_loader.GameDataLoader
import com.unqueam.gamingplatform.utils.GameRequestTestResource
import com.unqueam.gamingplatform.utils.UserTestResource
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

private const val HULK_USERNAME = "hulk"

class GamesIT : AbstractIntegrationTest() {

    private lateinit var gameDataLoader: GameDataLoader

    @BeforeEach
    fun setup(@Autowired gameDataLoader: GameDataLoader) {
        this.gameDataLoader = gameDataLoader
    }

    /**
     * Test cases
     * 1. Publish game - 201 created
     * 2. Fetch no hidden games published by username - 200 ok
     * 3. Fetch game by ID - 200 ok
     * 4. Fetch game by no registered ID - 404 not found
     * 5. Delete game by ID - 200 ok
     * 6. Delete game by no registered ID - 404 not found
     * 7. Update game by id - 200 ok
     * 8. Update game by no registered id - 404 not found
     * 9. A game cannot be update by user other than its publisher
     * 10. User can hide a game if he is the publisher
     * 11. User can expose a game if he is the publisher
     * 12. User cannot hide a game if he is not the publisher
     * 13. User cannot expose a game if he is not the publisher
     */

    @Test
    fun `1 Publish game - 201 created`() {
        val user = userDataLoader.fetchLoadedUser("hulk")
        val token = buildJwtTokenForUser(user)

        val game = GameRequestTestResource.buildGameRequest()
        val gameContent = objectMapper.writeValueAsString(game)

        postTo(API.ENDPOINT_GAMES, gameContent, token)
            .andExpect(status().isCreated)
            .andReturn()
    }

    @Test
    fun `2 Fetch no hidden games published by username - 200 ok`() {
        val queryParams: MultiValueMap<String, String> = LinkedMultiValueMap()
        queryParams.put("username", mutableListOf(HULK_USERNAME))
        queryParams.put("hidden", mutableListOf("false"))

        getTo(API.ENDPOINT_GAMES, LinkedMultiValueMap(), queryParams)
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].publisher.username").value(HULK_USERNAME))
            .andReturn()
    }

    @Test
    fun `3 Fetch game by ID - 200 ok`() {
        val gameId = 2

        getTo(API.ENDPOINT_GAMES + "/$gameId")
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.publisher.username").value(HULK_USERNAME))
            .andReturn()
    }

    @Test
    fun `4 Fetch game by no registered ID - 404 not found`() {
        val gameId = 9999999

        getTo(API.ENDPOINT_GAMES + "/$gameId")
            .andExpect(status().isNotFound)
            .andReturn()
    }

    @Test
    fun `5 Delete game by ID - 200 ok`() {
        val gameId = 1

        deleteTo(API.ENDPOINT_GAMES + "/$gameId")
            .andExpect(status().isOk)
            .andReturn()
    }

    @Test
    fun `6 Delete game by no registered ID (does not cause effect) - 200 ok`() {
        val gameId = 999999

        deleteTo(API.ENDPOINT_GAMES + "/$gameId")
            .andExpect(status().isOk)
            .andReturn()
    }

    @Test
    fun `7 Update game by id - 200 ok`() {
        val user = userDataLoader.fetchLoadedUser(HULK_USERNAME)
        val game = gameDataLoader.loadNewGame(GameRequestTestResource.buildGameRequest(), user)
        val token = buildJwtTokenForUser(user)

        val body = objectMapper.writeValueAsString(GameRequestTestResource.buildGameRequest())

        putTo(API.ENDPOINT_GAMES + "/${game.id}", body, token)
            .andExpect(status().isOk)
            .andReturn()
    }

    @Test
    fun `8 Update game by no registered id - 404 not found`() {
        val gameId = 999999

        val user = userDataLoader.fetchLoadedUser(HULK_USERNAME)
        val token = buildJwtTokenForUser(user)

        val gameRequestToUpdate = GameRequestTestResource.buildGameRequest()
        val body = objectMapper.writeValueAsString(gameRequestToUpdate)

        putTo(API.ENDPOINT_GAMES + "/$gameId", body, token)
            .andExpect(status().isNotFound)
            .andReturn()
    }

    @Test
    fun `9 A game cannot be updated by a user other than its publisher`() {
        val loadedGame = gameDataLoader.loadNewGame(GameRequestTestResource.buildGameRequest(), UserTestResource.buildUser())

        val anotherUser = userDataLoader.fetchLoadedUser(HULK_USERNAME)
        val token = buildJwtTokenForUser(anotherUser)

        val gameRequestToUpdate = GameRequestTestResource.buildGameRequest()
        val body = objectMapper.writeValueAsString(gameRequestToUpdate)

        putTo(API.ENDPOINT_GAMES + "/${loadedGame.id}", body, token)
            .andExpect(status().isUnauthorized)
            .andReturn()
    }

    @Test
    fun `10 User can hide a game if he is the publisher`() {
        val user = userDataLoader.fetchLoadedUser(HULK_USERNAME)
        val game = gameDataLoader.loadNewGame(GameRequestTestResource.buildGameRequest(), user)

        val token = buildJwtTokenForUser(user)

        putTo(API.ENDPOINT_GAMES + "/${game.id}/hide", "", token)
            .andExpect(status().isOk)
            .andReturn()
    }

    @Test
    fun `11 User can expose a game if he is the publisher`() {
        val user = userDataLoader.fetchLoadedUser(HULK_USERNAME)
        val game = gameDataLoader.loadNewGame(GameRequestTestResource.buildGameRequest(), user)

        val token = buildJwtTokenForUser(user)

        putTo(API.ENDPOINT_GAMES + "/${game.id}/expose", "", token)
            .andExpect(status().isOk)
            .andReturn()
    }

    @Test
    fun `12 User cannot hide a game if he is not the publisher`() {
        val publisher = userDataLoader.fetchLoadedUser("spider_man")
        val game = gameDataLoader.loadNewGame(GameRequestTestResource.buildGameRequest(), publisher)

        val anotherUser = userDataLoader.fetchLoadedUser(HULK_USERNAME)
        val token = buildJwtTokenForUser(anotherUser)

        putTo(API.ENDPOINT_GAMES + "/${game.id}/hide", "", token)
            .andExpect(status().isUnauthorized)
            .andReturn()
    }

    @Test
    fun `13 User cannot expose a game if he is not the publisher`() {
        val publisher = userDataLoader.fetchLoadedUser("spider_man")
        val game = gameDataLoader.loadNewGame(GameRequestTestResource.buildGameRequest(), publisher)

        val anotherUser = userDataLoader.fetchLoadedUser(HULK_USERNAME)
        val token = buildJwtTokenForUser(anotherUser)

        putTo(API.ENDPOINT_GAMES + "/${game.id}/expose", "", token)
            .andExpect(status().isUnauthorized)
            .andReturn()
    }
}