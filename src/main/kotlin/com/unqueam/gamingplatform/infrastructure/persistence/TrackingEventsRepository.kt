package com.unqueam.gamingplatform.infrastructure.persistence

import com.unqueam.gamingplatform.core.services.implementation.TrackingEvent
import org.springframework.data.jpa.repository.JpaRepository

interface TrackingEventsRepository : JpaRepository<TrackingEvent, Long>