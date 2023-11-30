package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.dtos.TrackingDTO
import com.unqueam.gamingplatform.core.services.ITrackingService
import com.unqueam.gamingplatform.core.tracking.DailyMetricsReport
import com.unqueam.gamingplatform.core.tracking.TrackingEntity
import com.unqueam.gamingplatform.core.tracking.TrackingEvent
import com.unqueam.gamingplatform.core.tracking.TrackingType
import com.unqueam.gamingplatform.infrastructure.persistence.TrackingEventsRepository
import org.springframework.scheduling.annotation.Async
import java.time.LocalDateTime

open class TrackingService : ITrackingService {

    private val trackingRepository: TrackingEventsRepository

    constructor(trackingRepository: TrackingEventsRepository) {
        this.trackingRepository = trackingRepository
    }

    @Async
    override fun trackViewEvent(trackingEntity: TrackingEntity, entityId: Long) {
        val trackingEvent = TrackingEvent(null, TrackingType.VIEW, trackingEntity, entityId, LocalDateTime.now())
        trackingRepository.save(trackingEvent)
    }

    @Async
    override fun track(trackingEntity: TrackingEntity, trackingType: TrackingType, entityId: Long, timeStamp: LocalDateTime) {
        val trackingEvent = TrackingEvent(null, trackingType, trackingEntity, entityId, timeStamp)
        trackingRepository.save(trackingEvent)
    }

    override fun getDailyMetrics(trackingDTO: TrackingDTO): DailyMetricsReport {
        val result = trackingRepository.getDailyMetrics(
            trackingDTO.entityId,
            trackingDTO.trackingEntity,
            trackingDTO.trackingType)

        return DailyMetricsReport(result)
    }
}
