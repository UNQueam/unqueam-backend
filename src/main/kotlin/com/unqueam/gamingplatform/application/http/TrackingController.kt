package com.unqueam.gamingplatform.application.http

import com.unqueam.gamingplatform.application.dtos.TrackingDTO
import com.unqueam.gamingplatform.core.services.ITrackingService
import com.unqueam.gamingplatform.core.tracking.TrackingEntity
import com.unqueam.gamingplatform.core.tracking.TrackingType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(API.ENDPOINT_TRACKING)
class TrackingController {

    private final val trackingService: ITrackingService

    @Autowired
    constructor(trackingService: ITrackingService) {
        this.trackingService = trackingService
    }

    @PostMapping
    fun track(@RequestBody trackingDTO: TrackingDTO) : ResponseEntity<Any> {
        trackingService.track(
            trackingDTO.trackingEntity,
            trackingDTO.trackingType,
            trackingDTO.entityId)

        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun dailyMetrics(
        @RequestParam("tracking_type") trackingType: TrackingType,
        @RequestParam("tracking_entity") trackingEntity: TrackingEntity,
        @RequestParam("entity_id") entityId: Long) : ResponseEntity<Any> {
        val trackingDTO = TrackingDTO(entityId, trackingEntity, trackingType)
        val metrics = trackingService.getDailyMetrics(trackingDTO)
        return ResponseEntity.ok(metrics)
    }
}
