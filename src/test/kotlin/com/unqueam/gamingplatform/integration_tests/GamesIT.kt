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

private const val ADMIN_USERNAME = "admin.j"


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
     * 14. User can comment on a game if he is not the publisher of the game
     * 15. User can not comment on a game if he is the publisher of the game
     * 16. User can update his own comment
     * 17. User can delete his own comment
     * 18. Admin can delete any comment
     * 19. User can not delete a comment if he is not admin nor is its owner
     * 20. User can not modify a comment if he is not its owner
     * 21. Update comment with invalid rating results in 400 Bad request
     * 22. Update comment with invalid content results in 400 Bad request
     * 23. Publish comment with invalid rating results in 400 Bad request
     * 24. Publish comment with invalid content results in 400 Bad request
     * 25. Update comment by no registered id - 404 not found
     * 26. Delete comment by no registered id - 404 not found
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

    @Test
    fun `14 User can comment on a game if he is not the publisher of the game`() {
        val publisher = userDataLoader.fetchLoadedUser("spider_man")
        val game = gameDataLoader.loadNewGame(GameRequestTestResource.buildGameRequest(), publisher)

        val anotherUser = userDataLoader.fetchLoadedUser(HULK_USERNAME)
        val token = buildJwtTokenForUser(anotherUser)

        val comment = GameRequestTestResource.buildCommentRequest()
        val commentContent = objectMapper.writeValueAsString(comment)

        postTo(API.ENDPOINT_GAMES + "/${game.id}/comments", commentContent, token)
                .andExpect(status().isCreated)
                .andReturn()
    }

    @Test
    fun `15 User can not comment on a game if he is the publisher of the game`() { //CAMBIAR
        val publisher = userDataLoader.fetchLoadedUser("spider_man")
        val game = gameDataLoader.loadNewGame(GameRequestTestResource.buildGameRequest(), publisher)

        val token = buildJwtTokenForUser(publisher)

        val comment = GameRequestTestResource.buildCommentRequest()
        val commentContent = objectMapper.writeValueAsString(comment)

        postTo(API.ENDPOINT_GAMES + "/${game.id}/comments", commentContent, token)
                .andExpect(status().isUnauthorized)
                .andReturn()
    }

    @Test
    fun `16 User can update its own comment`() {
        val publisher = userDataLoader.fetchLoadedUser("spider_man")
        val game = gameDataLoader.loadNewGame(GameRequestTestResource.buildGameRequest(), publisher)

        val anotherUser = userDataLoader.fetchLoadedUser(HULK_USERNAME)
        val token = buildJwtTokenForUser(anotherUser)

        val comment = gameDataLoader.loadNewComment(game.id!!,GameRequestTestResource.buildCommentRequest(),anotherUser)
        val newCommentValues = GameRequestTestResource.buildCommentRequest()

        val commentContent = objectMapper.writeValueAsString(newCommentValues)

        putTo(API.ENDPOINT_GAMES + "/${game.id}/comments/${comment.id}", commentContent, token)
                .andExpect(status().isOk)
                .andReturn()
    }

    @Test
    fun `17 User can delete his own comment`() {
        val publisher = userDataLoader.fetchLoadedUser("spider_man")
        val game = gameDataLoader.loadNewGame(GameRequestTestResource.buildGameRequest(), publisher)

        val anotherUser = userDataLoader.fetchLoadedUser(HULK_USERNAME)
        val token = buildJwtTokenForUser(anotherUser)

        val comment = gameDataLoader.loadNewComment(game.id!!,GameRequestTestResource.buildCommentRequest(),anotherUser)

        val headers: MultiValueMap<String, String> = LinkedMultiValueMap()
        headers.add("Authorization", token)

        deleteTo(API.ENDPOINT_GAMES + "/${game.id}/comments/${comment.id}", headers)
                .andExpect(status().isOk)
                .andReturn()
    }

    @Test
    fun `18 An admin can delete any comment`() {
        val publisher = userDataLoader.fetchLoadedUser("spider_man")
        val game = gameDataLoader.loadNewGame(GameRequestTestResource.buildGameRequest(), publisher)

        val anotherUser = userDataLoader.fetchLoadedUser(HULK_USERNAME)

        val comment = gameDataLoader.loadNewComment(game.id!!,GameRequestTestResource.buildCommentRequest(),anotherUser)

        val admin = userDataLoader.fetchLoadedUser(ADMIN_USERNAME)
        val adminToken = buildJwtTokenForUser(admin)

        val headers: MultiValueMap<String, String> = LinkedMultiValueMap()
        headers.add("Authorization", adminToken)

        deleteTo(API.ENDPOINT_GAMES + "/${game.id}/comments/${comment.id}", headers)
                .andExpect(status().isOk)
                .andReturn()
    }

    @Test
    fun `19 User can not delete a comment if he is not admin nor is his owner`() {
        val publisher = userDataLoader.fetchLoadedUser("spider_man")
        val game = gameDataLoader.loadNewGame(GameRequestTestResource.buildGameRequest(), publisher)

        val anotherUser = userDataLoader.fetchLoadedUser(HULK_USERNAME)

        val comment = gameDataLoader.loadNewComment(game.id!!,GameRequestTestResource.buildCommentRequest(),anotherUser)

        val anotherUserToken = buildJwtTokenForUser(publisher)

        val headers: MultiValueMap<String, String> = LinkedMultiValueMap()
        headers.add("Authorization", anotherUserToken)

        deleteTo(API.ENDPOINT_GAMES + "/${game.id}/comments/${comment.id}", headers)
                .andExpect(status().isUnauthorized)
                .andReturn()
    }

    @Test
    fun `20 User can not modify a comment if he is not its owner`() {
        val publisher = userDataLoader.fetchLoadedUser("spider_man")
        val game = gameDataLoader.loadNewGame(GameRequestTestResource.buildGameRequest(), publisher)
        val token = buildJwtTokenForUser(publisher)

        val anotherUser = userDataLoader.fetchLoadedUser(HULK_USERNAME)

        val comment = gameDataLoader.loadNewComment(game.id!!,GameRequestTestResource.buildCommentRequest(),anotherUser)
        val newCommentValues = GameRequestTestResource.buildCommentRequest()

        val commentContent = objectMapper.writeValueAsString(newCommentValues)

        putTo(API.ENDPOINT_GAMES + "/${game.id}/comments/${comment.id}", commentContent, token)
                .andExpect(status().isUnauthorized)
                .andReturn()
    }

    @Test
    fun `21 Update comment with invalid rating results in 400 Bad request`() {
        val publisher = userDataLoader.fetchLoadedUser("spider_man")
        val game = gameDataLoader.loadNewGame(GameRequestTestResource.buildGameRequest(), publisher)


        val anotherUser = userDataLoader.fetchLoadedUser(HULK_USERNAME)
        val token = buildJwtTokenForUser(anotherUser)


        val comment = gameDataLoader.loadNewComment(game.id!!,GameRequestTestResource.buildCommentRequest(),anotherUser)
        val invalidRating = 7
        val newCommentValues = GameRequestTestResource.buildCommentRequest(invalidRating)

        val commentContent = objectMapper.writeValueAsString(newCommentValues)

        putTo(API.ENDPOINT_GAMES + "/${game.id}/comments/${comment.id}", commentContent, token)
                .andExpect(status().isBadRequest)
                .andReturn()
    }

    @Test
    fun `22 Update comment with invalid content results in 400 Bad request`() {
        val publisher = userDataLoader.fetchLoadedUser("spider_man")
        val game = gameDataLoader.loadNewGame(GameRequestTestResource.buildGameRequest(), publisher)


        val anotherUser = userDataLoader.fetchLoadedUser(HULK_USERNAME)
        val token = buildJwtTokenForUser(anotherUser)


        val comment = gameDataLoader.loadNewComment(game.id!!,GameRequestTestResource.buildCommentRequest(),anotherUser)
        val invalidContent = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam dapibus nulla nec ex cursus, ut volutpat purus tristique. Vivamus euismod nibh nec hendrerit convallis. Integer tincidunt dolor nec felis viverra, sit amet congue tortor tempor. Phasellus id metus at justo feugiat dapibus ac in quam. In vehicula sed justo id dignissim. Sed congue, est nec pellentesque iaculis, erat nulla rhoncus arcu, a viverra risus urna eget nisl. Donec non ligula in sapien sodales interdum. Nam eu lectus metus. Curabitur ut neque ut augue venenatis tincidunt. Fusce posuere dolor ac est tincidunt, sit amet venenatis nulla consectetur. Aenean placerat justo vel laoreet congue. Sed dignissim volutpat nibh, eu cursus odio."
        val newCommentValues = GameRequestTestResource.buildCommentRequest(5, invalidContent)

        val commentContent = objectMapper.writeValueAsString(newCommentValues)

        putTo(API.ENDPOINT_GAMES + "/${game.id}/comments/${comment.id}", commentContent, token)
                .andExpect(status().isBadRequest)
                .andReturn()
    }

    @Test
    fun `23 publish comment with invalid rating results in 400 Bad request`() {
        val publisher = userDataLoader.fetchLoadedUser("spider_man")
        val game = gameDataLoader.loadNewGame(GameRequestTestResource.buildGameRequest(), publisher)


        val anotherUser = userDataLoader.fetchLoadedUser(HULK_USERNAME)
        val token = buildJwtTokenForUser(anotherUser)

        val invalidRating = 7
        val comment = GameRequestTestResource.buildCommentRequest(invalidRating)
        val commentContent = objectMapper.writeValueAsString(comment)

        postTo(API.ENDPOINT_GAMES + "/${game.id}/comments", commentContent, token)
                .andExpect(status().isBadRequest)
                .andReturn()
    }

    @Test
    fun `24 publish comment with invalid content results in 400 Bad request`() {
        val publisher = userDataLoader.fetchLoadedUser("spider_man")
        val game = gameDataLoader.loadNewGame(GameRequestTestResource.buildGameRequest(), publisher)


        val anotherUser = userDataLoader.fetchLoadedUser(HULK_USERNAME)
        val token = buildJwtTokenForUser(anotherUser)

        val invalidContent = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam dapibus nulla nec ex cursus, ut volutpat purus tristique. Vivamus euismod nibh nec hendrerit convallis. Integer tincidunt dolor nec felis viverra, sit amet congue tortor tempor. Phasellus id metus at justo feugiat dapibus ac in quam. In vehicula sed justo id dignissim. Sed congue, est nec pellentesque iaculis, erat nulla rhoncus arcu, a viverra risus urna eget nisl. Donec non ligula in sapien sodales interdum. Nam eu lectus metus. Curabitur ut neque ut augue venenatis tincidunt. Fusce posuere dolor ac est tincidunt, sit amet venenatis nulla consectetur. Aenean placerat justo vel laoreet congue. Sed dignissim volutpat nibh, eu cursus odio."
        val comment = GameRequestTestResource.buildCommentRequest(4, invalidContent)
        val commentContent = objectMapper.writeValueAsString(comment)

        postTo(API.ENDPOINT_GAMES + "/${game.id}/comments", commentContent, token)
                .andExpect(status().isBadRequest)
                .andReturn()
    }

    @Test
    fun `25 Update comment by no registered id - 404 not found`() {
        val publisher = userDataLoader.fetchLoadedUser("spider_man")
        val game = gameDataLoader.loadNewGame(GameRequestTestResource.buildGameRequest(), publisher)

        val anotherUser = userDataLoader.fetchLoadedUser(HULK_USERNAME)
        val token = buildJwtTokenForUser(anotherUser)

        val invalidId = 9999

        val comment = GameRequestTestResource.buildCommentRequest()

        val commentContent = objectMapper.writeValueAsString(comment)

        putTo(API.ENDPOINT_GAMES + "/${game.id}/comments/${invalidId}", commentContent, token)
                .andExpect(status().isNotFound)
                .andReturn()
    }

    @Test
    fun `26 Delete comment by no registered id - 404 not found`() {
        val publisher = userDataLoader.fetchLoadedUser("spider_man")
        val game = gameDataLoader.loadNewGame(GameRequestTestResource.buildGameRequest(), publisher)

        val anotherUser = userDataLoader.fetchLoadedUser(HULK_USERNAME)
        val token = buildJwtTokenForUser(anotherUser)

        val invalidId = 9999

        val headers: MultiValueMap<String, String> = LinkedMultiValueMap()
        headers.add("Authorization", token)

        deleteTo(API.ENDPOINT_GAMES + "/${game.id}/comments/${invalidId}", headers)
                .andExpect(status().isNotFound)
                .andReturn()
    }
}