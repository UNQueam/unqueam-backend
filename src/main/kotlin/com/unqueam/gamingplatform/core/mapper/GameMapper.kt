package com.unqueam.gamingplatform.core.mapper

import com.unqueam.gamingplatform.application.dtos.DeveloperGameInput
import com.unqueam.gamingplatform.application.dtos.GameImageInput
import com.unqueam.gamingplatform.application.dtos.GameRequest
import com.unqueam.gamingplatform.application.dtos.GenreInput
import com.unqueam.gamingplatform.core.domain.*

class GameMapper {

    fun map(aGameRequest: GameRequest): Game {
        return Game.builder()
            .named(aGameRequest.name)
            .developedBy(mapDevelopers(aGameRequest.developers))
            .describedAs(aGameRequest.description)
            .withLinkToGame(aGameRequest.linkToGame)
            .releasedAt(aGameRequest.releaseDate)
            .withGenres(mapGenres(aGameRequest.genres))
            .withImages(mapImages(aGameRequest.images))
            .withLogoUrl(aGameRequest.logoUrl)
            .build()
    }

    private fun mapGenres(genres: Set<GenreInput>): Set<Genre> {
        return genres.mapTo(mutableSetOf()) { Genre(null, it.name) }
    }

    private fun mapDevelopers(developersInput: Set<DeveloperGameInput>): Set<Developer> {
        return developersInput.mapTo(mutableSetOf()) { Developer(null, it.firstName, it.lastName) }
    }

    private fun mapImages(gameImages: Set<GameImageInput>): Set<GameImage> {
        return gameImages.mapTo(mutableSetOf()) { GameImage(null, it.url) }
    }
}