package com.unqueam.gamingplatform.core.services

import com.unqueam.gamingplatform.application.dtos.TrackingDTO
import com.unqueam.gamingplatform.core.tracking.DailyMetricsReport
import com.unqueam.gamingplatform.core.tracking.TrackingEntity
import com.unqueam.gamingplatform.core.tracking.TrackingType
import java.time.LocalDateTime

interface ITrackingService {

    fun trackViewEvent(trackingEntity: TrackingEntity, entityId: Long)
    fun track(trackingEntity: TrackingEntity, trackingType: TrackingType, entityId: Long, timeStamp: LocalDateTime = LocalDateTime.now())
    fun getDailyMetrics(trackingDTO: TrackingDTO): DailyMetricsReport
}