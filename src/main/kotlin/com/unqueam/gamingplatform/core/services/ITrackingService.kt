package com.unqueam.gamingplatform.core.services

import com.unqueam.gamingplatform.core.tracking.TrackingEntity

interface ITrackingService {

    fun trackViewEvent(trackingEntity: TrackingEntity, entityId: Long)
}