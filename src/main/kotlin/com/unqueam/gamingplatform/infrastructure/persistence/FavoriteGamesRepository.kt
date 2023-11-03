package com.unqueam.gamingplatform.infrastructure.persistence

import com.unqueam.gamingplatform.core.domain.FavoriteGame
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface FavoriteGamesRepository : JpaRepository<FavoriteGame, Long> {

    @Query("select favGame from FavoriteGame favGame INNER JOIN PlatformUser user ON favGame.platformUser.id = user.id where user.id = :userId")
    fun findAllByPlatformUser(userId: Long): List<FavoriteGame>
}