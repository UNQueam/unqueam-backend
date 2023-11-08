package com.unqueam.gamingplatform.integration_tests.data_loader

import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.core.services.IFavoriteGamesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("test")
class FavoriteGamesLoader(@Autowired private val favoriteGamesService: IFavoriteGamesService) {

    fun addFavoriteGame(gameId: Long, user: PlatformUser) {
        favoriteGamesService.addGameAsFavorite(user, gameId)
    }
}