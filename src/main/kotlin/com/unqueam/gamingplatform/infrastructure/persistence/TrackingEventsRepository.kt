package com.unqueam.gamingplatform.infrastructure.persistence

import com.unqueam.gamingplatform.core.tracking.TrackingEntity
import com.unqueam.gamingplatform.core.tracking.TrackingEvent
import com.unqueam.gamingplatform.core.tracking.TrackingType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface TrackingEventsRepository : JpaRepository<TrackingEvent, Long> {

    @Query("SELECT FUNCTION('DATE', te.timeStamp) AS date, COUNT(te) AS count " +
            "FROM TrackingEvent te " +
            "WHERE te.entityId = :entityId " +
            "AND te.trackingEntity = :trackingEntity " +
            "AND te.trackingType = :trackingType " +
            "GROUP BY FUNCTION('DATE', te.timeStamp)")
    fun getDailyMetrics(
        @Param("entityId") entityId: Long,
        @Param("trackingEntity") trackingEntity: TrackingEntity,
        @Param("trackingType") trackingType: TrackingType
    ): List<Map<String, Any>>

    @Query("SELECT COUNT(te) AS count " +
            "FROM TrackingEvent te " +
            "WHERE te.entityId = :entityId " +
            "AND te.trackingEntity = :trackingEntity " +
            "AND te.trackingType = :trackingType ")
    fun getTotal(
        @Param("entityId") entityId: Long,
        @Param("trackingEntity") trackingEntity: TrackingEntity,
        @Param("trackingType") trackingType: TrackingType
    ): Long
}