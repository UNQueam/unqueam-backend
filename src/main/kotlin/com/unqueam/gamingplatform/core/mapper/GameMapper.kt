package com.unqueam.gamingplatform.core.mapper

import com.unqueam.gamingplatform.application.dtos.*
import com.unqueam.gamingplatform.core.domain.*

class GameMapper {

    fun mapToInput(aGameRequest: GameRequest, publisher: PlatformUser): Game {
        return Game.builder()
            .named(aGameRequest.name)
            .developedBy(mapToSet(aGameRequest.developers) { Developer(null, it.firstName, it.lastName) })
            .describedAs(aGameRequest.description)
            .withLinkToGame(aGameRequest.linkToGame)
            .releasedAt(aGameRequest.releaseDate)
            .withGenres(mapToSet(aGameRequest.genres) { Genre.findGenre(it.name) })
            .withImages(mapToSet(aGameRequest.images) { GameImage(null, it.url) })
            .withLogoUrl(aGameRequest.logoUrl)
            .withDevelopmentTeam(aGameRequest.developmentTeam)
            .publishedBy(publisher)
            .isHidden(aGameRequest.isHidden ?: false)
            .build()
    }

    fun mapToOutput(aGame: Game): GameOutput {
        val publisherOutput = PublisherOutput(aGame.publisher.id!!, aGame.publisher.getUsername())
        return GameOutput(
            aGame.id!!,
            aGame.name,
            aGame.logoUrl,
            aGame.description,
            aGame.linkToGame,
            aGame.releaseDate,
            mapToSet(aGame.developers) { DeveloperGameOutput(it.id!!, it.firstName, it.lastName) },
            mapToSet(aGame.images) { GameImageOutput(it.id!!, it.url) },
            mapToSet(aGame.genres) { GenreOutput(it.name, it.spanishName, it.englishName) },
            aGame.developmentTeam,
            aGame.rankBadge.name,
            publisherOutput,
            aGame.isHidden,
            mapToSet(aGame.comments) { CommentOutput(publisherOutput, it.rating, it.content, it.creationTime, it.lastModification) }
        )
    }

    private inline fun <T, R, C : Collection<T>> mapToSet(source: C, transform: (T) -> R): MutableSet<R> {
        return source.mapTo(mutableSetOf()) { transform(it) }
    }
}