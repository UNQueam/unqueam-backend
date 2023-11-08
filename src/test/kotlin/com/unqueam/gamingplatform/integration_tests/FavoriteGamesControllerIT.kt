package com.unqueam.gamingplatform.integration_tests

import com.unqueam.gamingplatform.core.domain.Role
import com.unqueam.gamingplatform.infrastructure.configuration.jwt.JwtHelper
import com.unqueam.gamingplatform.integration_tests.data_loader.FavoriteGamesLoader
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.util.LinkedMultiValueMap

private const val USERNAME = "Jhon"
private const val PASSWORD = "Doe"

class FavoriteGamesControllerIT : AbstractIntegrationTest() {

    /*
    Test cases
    1. Fetch favorite games by userId - 200 Ok
    2. Add game to favorite for logged-in user - 201 OK
    3. Add game by no existent gameId - 404 Not found
    4. Remove game from favorites by game Id for logged-in user - 200 Ok
    5. Remove no added game from favorites - 404 not found
     */

    private lateinit var favoriteGamesLoader: FavoriteGamesLoader

    @BeforeEach
    fun setup(@Autowired favoriteGamesLoader: FavoriteGamesLoader) {
        this.favoriteGamesLoader = favoriteGamesLoader
    }

    @Test
    fun `1 - Fetch favorite games by userId - 200 Ok`() {
        val gameId: Long = 1
        val user = userDataLoader.loadNewUser(USERNAME, PASSWORD, role = Role.USER)

        favoriteGamesLoader.addFavoriteGame(gameId, user)

        getTo("/api/users/${user.id}/games/favorites")
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].game.id").value(gameId))
            .andReturn()
    }

    @Test
    fun `2 - Add game to favorite for logged-in user - 201 OK`() {
        val gameId: Long = 1
        val user = userDataLoader.loadNewUser(USERNAME, PASSWORD, role = Role.USER)
        val token = buildJwtTokenForUser(user)

        postTo("/api/games/favorites/${gameId}", "", token)
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.game.id").value(gameId))
            .andReturn()
    }

    @Test
    fun `3 - Add game by no existent gameId - 404 Not found`() {
        val gameId: Long = 9999
        val user = userDataLoader.loadNewUser(USERNAME, PASSWORD, role = Role.USER)
        val token = buildJwtTokenForUser(user)

        postTo("/api/games/favorites/${gameId}", "", token)
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andReturn()
    }

    @Test
    fun `4 - Remove game from favorites by game Id for logged-in user - 200 Ok`() {
        val gameId: Long = 1
        val user = userDataLoader.loadNewUser(USERNAME, PASSWORD, role = Role.USER)
        val token = buildJwtTokenForUser(user)

        favoriteGamesLoader.addFavoriteGame(gameId, user)

        val headers = LinkedMultiValueMap<String, String>()
        headers.put(JwtHelper.AUTH_HEADER_KEY, mutableListOf(token))

        deleteTo("/api/games/favorites/${gameId}", headers)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.game.id").value(gameId))
            .andReturn()
    }

    @Test
    fun `5 - Remove no added game from favorites - 404 not found`() {
        val gameId: Long = 3
        val user = userDataLoader.loadNewUser(USERNAME, PASSWORD, role = Role.USER)
        val token = buildJwtTokenForUser(user)

        val headers = LinkedMultiValueMap<String, String>()
        headers.put(JwtHelper.AUTH_HEADER_KEY, mutableListOf(token))

        deleteTo("/api/games/favorites/${gameId}", headers)
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andReturn()
    }
}