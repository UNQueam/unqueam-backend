package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.dtos.CommentInput
import com.unqueam.gamingplatform.application.dtos.CommentOutput
import com.unqueam.gamingplatform.application.dtos.GameOutput
import com.unqueam.gamingplatform.application.dtos.GameRequest
import com.unqueam.gamingplatform.application.http.GetHiddenGamesParam
import com.unqueam.gamingplatform.core.domain.*
import com.unqueam.gamingplatform.core.exceptions.*
import com.unqueam.gamingplatform.core.exceptions.Exceptions.COMMENT_NOT_FOUND_ERROR_MESSAGE
import com.unqueam.gamingplatform.core.exceptions.Exceptions.GAME_NOT_FOUND_ERROR_MESSAGE
import com.unqueam.gamingplatform.core.exceptions.Exceptions.GAME_NOT_FOUND_ERROR_MESSAGE_ALIAS
import com.unqueam.gamingplatform.core.exceptions.Exceptions.THE_ALIAS_IS_ALREADY_IN_USE

import com.unqueam.gamingplatform.core.exceptions.comments.CanNotDeleteCommentException
import com.unqueam.gamingplatform.core.exceptions.comments.CanNotPublishCommentException
import com.unqueam.gamingplatform.core.exceptions.comments.CanNotUpdateCommentException
import com.unqueam.gamingplatform.core.exceptions.comments.InvalidCommentContentException
import com.unqueam.gamingplatform.core.mapper.CommentMapper
import com.unqueam.gamingplatform.core.exceptions.UserIsNotThePublisherOfTheGameException
import com.unqueam.gamingplatform.core.helper.KebabConverter.toKebabCase

import com.unqueam.gamingplatform.core.mapper.GameMapper
import com.unqueam.gamingplatform.core.services.IGameService
import com.unqueam.gamingplatform.core.services.ITrackingService
import com.unqueam.gamingplatform.core.tracking.TrackingEntity
import com.unqueam.gamingplatform.infrastructure.persistence.CommentRepository
import com.unqueam.gamingplatform.infrastructure.persistence.GameAndViewsRow
import com.unqueam.gamingplatform.infrastructure.persistence.GameRepository
import jakarta.persistence.EntityNotFoundException
import java.util.*

class GameService : IGameService {

    private val gameRepository: GameRepository
    private val gameMapper: GameMapper
    private val trackingService: ITrackingService
    private val commentMapper: CommentMapper
    private val commentRepository: CommentRepository

    constructor(aGameRepository: GameRepository, aGameMapper: GameMapper, trackingService: ITrackingService, aCommentMapper: CommentMapper,
            aCommentRepository: CommentRepository) {
        this.gameRepository = aGameRepository
        this.gameMapper = aGameMapper
        this.trackingService = trackingService
        this.commentMapper = aCommentMapper
        this.commentRepository = aCommentRepository
    }

    override fun publishGame(gameRequest: GameRequest, publisher: PlatformUser): Game {
        validateRequest(gameRequest)
        val game = gameMapper.mapToInput(gameRequest, publisher)
        return gameRepository.save(game)
    }

    override fun fetchGames(username: Optional<String>, getHiddenGamesParam: GetHiddenGamesParam): List<GameOutput> {
        lateinit var games: List<Game>
        if (getHiddenGamesParam.shouldFetchAllGames()) {
            games = username
                .map { gameRepository.findGamesByUsername(it) }
                .orElseGet { gameRepository.findAll() }
        } else {
            games = username
                .map { gameRepository.findNoHiddenGamesByUsername(it) }
                .orElseGet { gameRepository.findNoHiddenGames() }
        }
        return games.map { gameMapper.mapToOutput(it) }
    }

    override fun fetchGameByAlias(alias: String): GameOutput {
        val gameAndViewsRow: GameAndViewsRow = gameRepository
                .findGameAndCountViewsWithAlias(alias)
                .orElseThrow { EntityNotFoundException(GAME_NOT_FOUND_ERROR_MESSAGE_ALIAS.format(alias)) }

        return handleViewsTrackingAndGetUpdatedGameIfNeeded(gameAndViewsRow)
    }

    private fun handleViewsTrackingAndGetUpdatedGameIfNeeded(gameAndViewsRow: GameAndViewsRow): GameOutput {
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

    override fun updateGameById(id: Long, updatedGameRequest: GameRequest, publisher: PlatformUser) {
        validateRequest(updatedGameRequest)
        val updatedGameFromRequest = gameMapper.mapToInput(updatedGameRequest, publisher)

        val storedGame = getStoredGame(id)

        verifyIfIsPublisherFromGame(publisher, storedGame)

        val updatedGame = storedGame.syncWith(updatedGameFromRequest)

        gameRepository.save(updatedGame)
    }

    override fun hideGameById(id: Long, publisher: PlatformUser) {

        val storedGame = getStoredGame(id)

        verifyIfIsPublisherFromGame(publisher, storedGame)

        storedGame.isHidden = true

        gameRepository.save(storedGame)
    }

    override fun exposeGameById(id: Long, publisher: PlatformUser) {

        val storedGame = getStoredGame(id)

        verifyIfIsPublisherFromGame(publisher, storedGame)

        storedGame.isHidden = false

        gameRepository.save(storedGame)
    }

    private fun validateRequest(gameRequest: GameRequest) {
        val errors: MutableMap<String, List<String>> = mutableMapOf()

        if (gameRepository.existsByAlias(toKebabCase(gameRequest.alias)))
            errors["alias"] = listOf(THE_ALIAS_IS_ALREADY_IN_USE)


        if (errors.isNotEmpty()) throw GameFormException(errors)
    }

    private fun getStoredGame(id: Long): Game {
        return gameRepository
                .findById(id)
                .orElseThrow { EntityNotFoundException(GAME_NOT_FOUND_ERROR_MESSAGE.format(id)) }
    }

    private fun getStoredComment(id: Long): Comment {
        return commentRepository
                .findById(id)
                .orElseThrow { EntityNotFoundException(COMMENT_NOT_FOUND_ERROR_MESSAGE.format(id)) }
    }


    private fun verifyIfIsPublisherFromGame(publisher: PlatformUser, game: Game) {
        if (publisher.id != game.publisher.id) throw UserIsNotThePublisherOfTheGameException()
    }

    override fun publishComment(gameId: Long, commentInput: CommentInput, publisher: PlatformUser): Comment {
        val storedGame = getStoredGame(gameId)

        verifyIfCanPublishComment(publisher, storedGame)
        verifyCommentValidity(commentInput.rating,commentInput.content)

        val comment = this.commentMapper.mapToInput(commentInput, publisher, storedGame)

        return commentRepository.save(comment)
    }

    override fun updateComment(commentId: Long,  commentInput: CommentInput, publisher: PlatformUser) : CommentOutput {

        val storedComment = getStoredComment(commentId)

        verifyIfCanUpdateComment(publisher, storedComment)
        verifyCommentValidity(commentInput.rating,commentInput.content)

        storedComment.update(commentInput.content, commentInput.rating)

        return commentMapper.mapToOutput(commentRepository.save(storedComment))
    }

    override fun deleteComment(commentId: Long, publisher: PlatformUser) {

        val storedComment = getStoredComment(commentId)
        verifyIfCanDeleteComment(publisher, storedComment)

        commentRepository.deleteById(commentId)
    }

    private fun verifyIfCanPublishComment(publisher: PlatformUser, game: Game) {
        if (publisher.id === game.publisher.id) throw CanNotPublishCommentException()
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