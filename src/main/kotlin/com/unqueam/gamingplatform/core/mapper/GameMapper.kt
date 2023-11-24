package com.unqueam.gamingplatform.core.mapper

import com.unqueam.gamingplatform.application.dtos.*
import com.unqueam.gamingplatform.core.domain.*
import java.util.*

class GameMapper {

    fun mapToInput(aGameRequest: GameRequest, publisher: PlatformUser): Game {
        return Game.builder()
            .named(aGameRequest.name)
            .developedBy(mapToSet(aGameRequest.developers) { Developer(null, it.name) })
            .describedAs(aGameRequest.description)
            .withLinkToGame(aGameRequest.linkToGame)
            .releasedAt(aGameRequest.releaseDate)
            .withGenres(mapToSet(aGameRequest.genres) { Genre.findGenre(it.name) })
            .withImages(mapToSet(aGameRequest.images) { GameImage(null, it.url) })
            .withLogoUrl(aGameRequest.logoUrl)
            .withDevelopmentTeam(aGameRequest.developmentTeam)
            .publishedBy(publisher)
            .isHidden(aGameRequest.isHidden ?: false)
            .withAlias(aGameRequest.alias)
            .withLinkToDownload(aGameRequest.linkToDownload)
            .withPeriod(mapToPeriod(aGameRequest.period))
            .build()
    }

    fun mapToOutput(aGame: Game): GameOutput {
        val publisherOutput = PublisherOutput(
            aGame.publisher.id!!,
            aGame.publisher.getUsername(),
            aGame.publisher.getProfile().imageId
        )
        return GameOutput(
            aGame.id!!,
            aGame.name,
            aGame.logoUrl,
            aGame.description,
            aGame.linkToGame,
            aGame.releaseDate,
            mapToSet(aGame.developers) { DeveloperGameOutput(it.id!!, it.name) },
            mapToSet(aGame.images) { GameImageOutput(it.id!!, it.url) },
            mapToSet(aGame.genres) { GenreOutput(it.name, it.spanishName, it.englishName) },
            aGame.developmentTeam,
            aGame.rankBadge.name,
            publisherOutput,
            aGame.isHidden,
            mapToSet(aGame.comments) { CommentOutput(it.id!!, aGame.id!!, PublisherOutput(it.getPublisherId(), it.publisher.getUsername(), it.publisher.getProfile().imageId), it.rating, it.content, it.creationTime, it.lastModification) },
            aGame.alias,
            aGame.linkToDownload,
            mapToPeriodDTO(aGame.period)
        )
    }

    private fun mapToPeriodDTO(period: Period?): PeriodDTO? {
        if (period == null) return null
        return PeriodDTO(
            id = period.id,
            year = period.year,
            semester = period.semester
        )
    }

    private fun mapToPeriod(periodDTO: PeriodDTO?): Period? {
        if (periodDTO == null) return null
        return Period(
            anId = periodDTO.id,
            aYear = periodDTO.year,
            aSemester = periodDTO.semester
        )
    }

    private inline fun <T, R, C : Collection<T>> mapToSet(source: C, transform: (T) -> R): MutableSet<R> {
        return source.mapTo(mutableSetOf()) { transform(it) }
    }
}