package com.unqueam.gamingplatform.core.services

import com.unqueam.gamingplatform.application.dtos.FavoriteGameOutput
import com.unqueam.gamingplatform.core.domain.PlatformUser

interface IFavoriteGamesService {

    fun findFavoriteGamesByUser(userId: Long): Collection<FavoriteGameOutput>
    fun addGameAsFavorite(authenticatedUser: PlatformUser, gameId: Long): FavoriteGameOutput
    fun deleteGameFromFavorites(authenticatedUser: PlatformUser, gameId: Long): FavoriteGameOutput
}