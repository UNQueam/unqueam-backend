package com.unqueam.gamingplatform.infrastructure.persistence

import com.unqueam.gamingplatform.core.domain.FavoriteGame
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface FavoriteGamesRepository : JpaRepository<FavoriteGame, Long> {

    @Query("SELECT game FROM PlatformUser user JOIN user.favoritesGames game WHERE user.id = :userId")
    fun findAllByPlatformUser(userId: Long): List<FavoriteGame>

    @Query("SELECT game FROM PlatformUser user JOIN user.favoritesGames game WHERE user.id = :userId AND game.id = :gameId")
    fun findByPlatformUserIdAndGameId(userId: Long, gameId: Long): Optional<FavoriteGame>
}