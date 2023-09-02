package com.unqueam.gamingplatform.infrastructure.persistence

import com.unqueam.gamingplatform.core.tracking.TrackingEvent
import org.springframework.data.jpa.repository.JpaRepository

interface TrackingEventsRepository : JpaRepository<TrackingEvent, Long>