package com.unqueam.gamingplatform.core.services

import com.unqueam.gamingplatform.application.dtos.CommentInput
import com.unqueam.gamingplatform.application.dtos.CommentInput
import com.unqueam.gamingplatform.application.dtos.CommentOutput
import com.unqueam.gamingplatform.application.dtos.GameOutput
import com.unqueam.gamingplatform.application.dtos.GameRequest
import com.unqueam.gamingplatform.application.http.GetHiddenGamesParam

import com.unqueam.gamingplatform.core.domain.Comment

import com.unqueam.gamingplatform.core.domain.Game
import com.unqueam.gamingplatform.core.domain.Game
import com.unqueam.gamingplatform.core.domain.PlatformUser
import java.util.*

interface IGameService {

    fun publishGame(gameRequest: GameRequest, publisher: PlatformUser): Game
    fun fetchGames(username: Optional<String>, getHiddenGamesParam: GetHiddenGamesParam): List<GameOutput>
    fun fetchGameById(id: Long): GameOutput
    fun deleteGameById(id: Long)
    fun updateGameById(id: Long, updatedGameRequest: GameRequest, publisher: PlatformUser)
    fun hideGameById(id: Long, publisher: PlatformUser)
    fun exposeGameById(id: Long, publisher: PlatformUser)
    fun publishComment(gameId: Long, commentInput: CommentInput, publisher: PlatformUser) : CommentOutput
    fun updateComment(commentId: Long,  commentInput: CommentInput, publisher: PlatformUser) : CommentOutput
    fun deleteComment(commentId: Long, publisher: PlatformUser)
}