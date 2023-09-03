package com.unqueam.gamingplatform.core.mapper

import com.unqueam.gamingplatform.application.dtos.DeveloperGameInput
import com.unqueam.gamingplatform.application.dtos.GameImageInput
import com.unqueam.gamingplatform.application.dtos.GameRequest
import com.unqueam.gamingplatform.core.domain.Developer
import com.unqueam.gamingplatform.core.domain.Game
import com.unqueam.gamingplatform.core.domain.GameImage
import com.unqueam.gamingplatform.core.domain.RankBadge

class GameMapper {

    fun map(aGameRequest: GameRequest): Game {
        return Game(
            null,
            aGameRequest.name,
            aGameRequest.logoUrl,
            aGameRequest.description,
            aGameRequest.linkToGame,
            aGameRequest.releaseDate,
            mapDevelopers(aGameRequest.developers),
            mapImages(aGameRequest.images),
            RankBadge.UNRANKED
        )
    }

    private fun mapDevelopers(developersInput: Set<DeveloperGameInput>): Set<Developer> {
        val set = mutableSetOf<Developer>()
        return developersInput.mapTo(set) { Developer(null, it.firstName, it.lastName) }
    }

    private fun mapImages(gameImages: Set<GameImageInput>): Set<GameImage> {
        val set = mutableSetOf<GameImage>()
        return gameImages.mapTo(set) { GameImage(null, it.url) }
    }
}