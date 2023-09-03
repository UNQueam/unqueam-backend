package com.unqueam.gamingplatform.infrastructure.persistence

import com.unqueam.gamingplatform.core.domain.Game
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface GameRepository : JpaRepository<Game, Long> {

    @Query(
        "SELECT new com.unqueam.gamingplatform.infrastructure.persistence.GameAndViewsRow(g, COALESCE(COUNT(te), 0)) " +
                "FROM Game g " +
                "LEFT JOIN TrackingEvent te " +
                "ON g.id = te.entityId " +
                "AND te.trackingEntity = 'game' " +
                "AND te.trackingType = 'view' " +
                "WHERE g.id = :id " +
                "GROUP BY g"
    )
    fun findGameAndCountViews(@Param("id") id: Long?): Optional<GameAndViewsRow>

    @EntityGraph(attributePaths=["developers", "images"])
    override fun findAll(): List<Game>
}

