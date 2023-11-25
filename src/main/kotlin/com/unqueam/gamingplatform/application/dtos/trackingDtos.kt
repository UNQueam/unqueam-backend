package com.unqueam.gamingplatform.application.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.unqueam.gamingplatform.core.tracking.TrackingEntity
import com.unqueam.gamingplatform.core.tracking.TrackingType

data class TrackingDTO(
    @JsonProperty("entity_id")
    val entityId: Long,
    @JsonProperty("tracking_entity")
    val trackingEntity: TrackingEntity,
    @JsonProperty("tracking_type")
    val trackingType: TrackingType
)