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
                    "WHERE g.alias = :alias " +
                    "GROUP BY g"
    )
    fun findGameAndCountViewsWithAlias(@Param("alias") alias: String?): Optional<GameAndViewsRow>

    @EntityGraph(attributePaths=["developers", "images", "genres", "publisher"])
    override fun findAll(): List<Game>

    fun existsByAlias(alias: String): Boolean

    @EntityGraph(attributePaths=["developers", "images", "genres", "publisher"])
    @Query("from Game game where game.publisher.username = :username")
    fun findGamesByUsername(@Param("username") username: String): List<Game>

    @EntityGraph(attributePaths=["developers", "images", "genres", "publisher"])
    @Query("from Game game where game.publisher.username = :username and game.isHidden = false")
    fun findNoHiddenGamesByUsername(username: String): List<Game>

    @EntityGraph(attributePaths=["developers", "images", "genres", "publisher"])
    @Query("from Game game where game.isHidden = false")
    fun findNoHiddenGames(): List<Game>
}

