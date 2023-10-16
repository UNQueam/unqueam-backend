package com.unqueam.gamingplatform.utils

import com.unqueam.gamingplatform.application.dtos.CommentInput
import com.unqueam.gamingplatform.application.dtos.GameRequest
import java.time.LocalDate

object GameRequestTestResource {

    fun buildGameRequest(): GameRequest {
        return GameRequest(
            "game name",
            "url",
            "description",
            "link",
            LocalDate.now(),
            setOf(),
            setOf(),
            setOf(),
            "developmentTeam",
            null
        )
    }

    fun buildCommentRequest(rating: Int = 5, content: String = "Best game ever made"): CommentInput {
        return CommentInput(
                rating,
                content
        )
    }

}
