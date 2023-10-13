package com.unqueam.gamingplatform.integration_tests

import com.unqueam.gamingplatform.application.http.API
import com.unqueam.gamingplatform.integration_tests.data_loader.GameDataLoader
import com.unqueam.gamingplatform.integration_tests.data_loader.UserDataLoader
import com.unqueam.gamingplatform.utils.GameRequestTestResource
import com.unqueam.gamingplatform.utils.UserTestResource.buildUser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

class GamesIT : AbstractIntegrationTest() {

    private lateinit var userDataLoader: UserDataLoader
    private lateinit var gameDataLoader: GameDataLoader

    @BeforeEach
    fun setup(@Autowired userDataLoader: UserDataLoader, @Autowired               gameDataLoader: GameDataLoader) {
        this.userDataLoader = userDataLoader
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
     */

    @Test
    fun `1 Publish game - 201 created`() {
        mockAuthenticatedUser(buildUser())

        val game = GameRequestTestResource.buildGameRequest()
        val gameContent = objectMapper.writeValueAsString(game)

        postTo(API.ENDPOINT_GAMES, gameContent)
            .andExpect(status().isCreated)
    }

    @Test
    fun `2 Fetch no hidden games published by username - 200 ok`() {
        val usernameToFind = "hulk"

        val queryParams: MultiValueMap<String, String> = LinkedMultiValueMap()
        queryParams.put("username", mutableListOf(usernameToFind))
        queryParams.put("hidden", mutableListOf("false"))

        getTo(API.ENDPOINT_GAMES, LinkedMultiValueMap(), queryParams)
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].publisher.username").value(usernameToFind))
    }

    @Test
    fun `3 Fetch game by ID - 200 ok`() {
        val gameId = 2

        getTo(API.ENDPOINT_GAMES + "/$gameId")
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.publisher.username").value("hulk"))
    }

    @Test
    fun `4 Fetch game by no registered ID - 404 not found`() {
        val gameId = 9999999

        getTo(API.ENDPOINT_GAMES + "/$gameId")
            .andExpect(status().isNotFound)
    }

    @Test
    fun `5 Delete game by ID - 200 ok`() {
        val gameId = 1

        deleteTo(API.ENDPOINT_GAMES + "/$gameId")
            .andExpect(status().isOk)
    }

    @Test
    fun `6 Delete game by no registered ID (does not cause effect) - 200 ok`() {
        val gameId = 999999

        deleteTo(API.ENDPOINT_GAMES + "/$gameId")
            .andExpect(status().isOk)
    }

    @Test
    fun `7 Update game by id - 200 ok`() {
        val gameId = 1

        mockAuthenticatedUser(buildUser())

        val gameRequestToUpdate = GameRequestTestResource.buildGameRequest()
        val body = objectMapper.writeValueAsString(gameRequestToUpdate)

        putTo(API.ENDPOINT_GAMES + "/$gameId", body)
            .andExpect(status().isOk)
    }

    @Test
    fun `8 Update game by no registered id - 404 not found`() {
        val gameId = 999999

        mockAuthenticatedUser(buildUser())

        val gameRequestToUpdate = GameRequestTestResource.buildGameRequest()
        val body = objectMapper.writeValueAsString(gameRequestToUpdate)

        putTo(API.ENDPOINT_GAMES + "/$gameId", body)
            .andExpect(status().isNotFound)
    }
}