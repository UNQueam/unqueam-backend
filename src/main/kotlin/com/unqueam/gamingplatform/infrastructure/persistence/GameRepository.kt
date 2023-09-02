package com.unqueam.gamingplatform.infrastructure.persistence

import com.unqueam.gamingplatform.core.domain.Game
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface GameRepository : JpaRepository<Game, Long> {

    @Query(
        "SELECT new com.unqueam.gamingplatform.infrastructure.persistence.GameAndViews(g, COUNT(te)) " +
                "FROM Game g " +
                "LEFT JOIN TrackingEvent te " +
                "ON g.id = te.entityId " +
                "WHERE te.trackingEntity = 'game' " +
                "AND te.trackingType = 'view' " +
                "AND g.id = :id " +
                "GROUP BY g"
    )
    fun findGameAndCountViews(@Param("id") id: Long?): Optional<GameAndViews>
}

data class GameAndViews(val game: Game, val views: Long)