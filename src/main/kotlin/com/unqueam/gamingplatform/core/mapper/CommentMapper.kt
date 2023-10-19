package com.unqueam.gamingplatform.core.mapper

import com.unqueam.gamingplatform.application.dtos.CommentInput
import com.unqueam.gamingplatform.application.dtos.CommentOutput
import com.unqueam.gamingplatform.application.dtos.PublisherOutput
import com.unqueam.gamingplatform.core.domain.*
import java.time.LocalDate

class CommentMapper {

    fun mapToInput(commentInput: CommentInput, user: PlatformUser, game: Game): Comment {
        return Comment(
                null,
                commentInput.rating,
                commentInput.content,
                game,
                user,
                LocalDate.now(),
                LocalDate.now()
        )
    }

    fun mapToOutput(comment: Comment): CommentOutput {
        return CommentOutput(
                comment.id!!,
                comment.gameId(),
                PublisherOutput(comment.publisher.id!!, comment.publisher.getUsername()),
                comment.rating,
                comment.content,
                comment.creationTime,
                comment.lastModification
        )
    }
}