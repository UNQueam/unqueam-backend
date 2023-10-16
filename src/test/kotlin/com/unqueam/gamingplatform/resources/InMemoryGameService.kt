package com.unqueam.gamingplatform.resources

import com.unqueam.gamingplatform.application.dtos.CommentInput
import com.unqueam.gamingplatform.application.dtos.GameOutput
import com.unqueam.gamingplatform.application.dtos.GameRequest
import com.unqueam.gamingplatform.application.http.GetHiddenGamesParam
import com.unqueam.gamingplatform.core.domain.Comment
import com.unqueam.gamingplatform.core.domain.Game
import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.core.domain.Role
import com.unqueam.gamingplatform.core.exceptions.Exceptions
import com.unqueam.gamingplatform.core.exceptions.Exceptions.GAME_NOT_FOUND_ERROR_MESSAGE
import com.unqueam.gamingplatform.core.exceptions.UserIsNotThePublisherOfTheGameException
import com.unqueam.gamingplatform.core.exceptions.comments.CanNotDeleteCommentException
import com.unqueam.gamingplatform.core.exceptions.comments.CanNotPublishCommentException
import com.unqueam.gamingplatform.core.exceptions.comments.CanNotUpdateCommentException
import com.unqueam.gamingplatform.core.exceptions.comments.InvalidCommentContentException
import com.unqueam.gamingplatform.core.mapper.CommentMapper
import com.unqueam.gamingplatform.core.mapper.GameMapper
import com.unqueam.gamingplatform.core.services.IGameService
import jakarta.persistence.EntityNotFoundException
import java.util.*

class InMemoryGameService : IGameService {

    private var gamesCounter: Long = 1
    private var gameRepository: MutableList<Game> = mutableListOf()
    private var commentRepository: MutableList<Comment> = mutableListOf()
    private val gameMapper: GameMapper = GameMapper()
    private val commentMapper: CommentMapper = CommentMapper()

    override fun publishGame(gameRequest: GameRequest, publisher: PlatformUser): Game {
        val game = gameMapper.mapToInput(gameRequest, publisher)
        game.id = gamesCounter
        gamesCounter++
        gameRepository.add(game)
        return game
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

        val storedGame = findGameInRepository(id)

        storedGame.syncWith(updatedGameFromRequest)
    }

    override fun hideGameById(id: Long, publisher: PlatformUser) {
        val storedGame = findGameInRepository(id)
        verifyIfIsPublisherFromGame(publisher, storedGame)

        storedGame.isHidden = true
    }

    override fun exposeGameById(id: Long, publisher: PlatformUser) {
        val storedGame = findGameInRepository(id)
        verifyIfIsPublisherFromGame(publisher, storedGame)

        storedGame.isHidden = false
    }

    override fun publishComment(gameId: Long, commentInput: CommentInput, publisher: PlatformUser): Comment {
        val storedGame = findGameInRepository(gameId)

        verifyIfCanPublishComment(publisher, storedGame)
        verifyCommentValidity(commentInput.rating,commentInput.content)

        val comment = this.commentMapper.mapToInput(commentInput, publisher, storedGame) // ver como seguir

        return comment // A CAMBIAR.
    }

    override fun updateComment(commentId: Long, commentInput: CommentInput, publisher: PlatformUser) {
        TODO("Not yet implemented")
    }

    override fun deleteComment(commentId: Long, publisher: PlatformUser) {
        TODO("Not yet implemented")
    }

    private fun findGameInRepository(id: Long): Game {
        return gameRepository.find { it.id == id } ?: throw EntityNotFoundException(GAME_NOT_FOUND_ERROR_MESSAGE.format(id))
    }

    private fun verifyIfIsPublisherFromGame(publisher: PlatformUser, game: Game) {
        if (publisher.id != game.publisher.id) throw UserIsNotThePublisherOfTheGameException()
    }

    private fun verifyIfCanPublishComment(publisher: PlatformUser, game: Game) {
        if (publisher.id != game.publisher.id) throw CanNotPublishCommentException()
    }

    private fun verifyIfCanUpdateComment(publisher: PlatformUser, comment: Comment) {
        if (comment.getPublisherId() != publisher.id) throw CanNotUpdateCommentException()
    }

    private fun verifyIfCanDeleteComment(publisher: PlatformUser, comment: Comment) {
        if (publisher.getRole() != Role.ADMIN && comment.getPublisherId() != publisher.id) throw CanNotDeleteCommentException()
    }

    private fun verifyCommentValidity(rating: Int, content: String) {
        val errors: MutableMap<String, List<String>> = mutableMapOf()

        if (rating !in 1..5) errors["rating"] = listOf(Exceptions.INVALID_COMMENT_RATING)
        if (content.length > 250)  errors["comments"] = listOf(Exceptions.INVALID_COMMENT_CONTENT)

        if (errors.isNotEmpty()) throw InvalidCommentContentException(errors)
    }

}