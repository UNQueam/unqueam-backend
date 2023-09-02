package com.unqueam.gamingplatform.core.services.implementation

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class TrackingEvent(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    @Enumerated (EnumType.STRING)
    val trackingType: TrackingType,
    @Enumerated (EnumType.STRING)
    val trackingEntity: TrackingEntity,
    val entityId: Long,
    val timeStamp: LocalDateTime
)

