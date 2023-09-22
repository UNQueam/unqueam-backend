package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.core.services.ITrackingService
import com.unqueam.gamingplatform.core.tracking.TrackingEntity
import com.unqueam.gamingplatform.core.tracking.TrackingEvent
import com.unqueam.gamingplatform.core.tracking.TrackingType
import com.unqueam.gamingplatform.infrastructure.persistence.TrackingEventsRepository
import org.springframework.scheduling.annotation.Async
import java.time.LocalDateTime

class TrackingService : ITrackingService {

    private val trackingRepository: TrackingEventsRepository

    constructor(trackingRepository: TrackingEventsRepository) {
        this.trackingRepository = trackingRepository
    }

    @Async
    override fun trackViewEvent(trackingEntity: TrackingEntity, entityId: Long) {
        val trackingEvent = TrackingEvent(null, TrackingType.VIEW, trackingEntity, entityId, LocalDateTime.now())
        trackingRepository.save(trackingEvent)
    }

}