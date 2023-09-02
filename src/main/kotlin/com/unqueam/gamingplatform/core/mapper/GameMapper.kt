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
            RankBadge.RANK_1
        )
    }

    private fun mapDevelopers(developersInput: List<DeveloperGameInput>): List<Developer> {
        return developersInput.map { Developer(null, it.firstName, it.lastName) }
    }

    private fun mapImages(gameImages: List<GameImageInput>): List<GameImage> {
        return gameImages.map { GameImage(null, it.url) }
    }
}