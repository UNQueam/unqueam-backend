package com.unqueam.gamingplatform.infrastructure.persistence

import com.unqueam.gamingplatform.core.domain.FavoriteGame
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface FavoriteGamesRepository : JpaRepository<FavoriteGame, Long> {

    @Query ("select favGame from FavoriteGame favGame " +
            "INNER JOIN PlatformUser user " +
            "ON favGame.platformUser.id = user.id " +
            "where user.id = :userId " +
            "AND favGame.game.isHidden = false")
    fun findAllByPlatformUser(userId: Long): List<FavoriteGame>

    @Query ("from FavoriteGame favGame where favGame.game.id = :gameId")
    fun findFavoriteGameByGameId(gameId: Long): Optional<FavoriteGame>
}