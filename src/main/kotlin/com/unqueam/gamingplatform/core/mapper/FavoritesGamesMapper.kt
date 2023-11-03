package com.unqueam.gamingplatform.core.mapper

import com.unqueam.gamingplatform.application.dtos.FavoriteGameOutput
import com.unqueam.gamingplatform.core.domain.FavoriteGame

class FavoritesGamesMapper {

    private val gameMapper: GameMapper

    constructor(gameMapper: GameMapper) {
        this.gameMapper = gameMapper
    }

    fun mapToOutput(favoriteGame: FavoriteGame): FavoriteGameOutput {
        return FavoriteGameOutput(
            id = favoriteGame.id!!,
            game = gameMapper.mapToOutput(favoriteGame.game())
        )
    }
}