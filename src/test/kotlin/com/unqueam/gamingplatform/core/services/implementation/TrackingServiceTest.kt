package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.core.tracking.TrackingEntity
import com.unqueam.gamingplatform.core.tracking.TrackingEvent
import com.unqueam.gamingplatform.infrastructure.persistence.TrackingEventsRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.verify

class TrackingServiceTest {

    private lateinit var trackingRepository: TrackingEventsRepository
    private lateinit var trackingService : TrackingService

    @BeforeEach
    fun setup() {
        trackingRepository = Mockito.mock(TrackingEventsRepository::class.java)
        trackingService = TrackingService(trackingRepository)
    }

    @Test
    fun `should track a view event`() {
        val captor = ArgumentCaptor.forClass(TrackingEvent::class.java)

        val entity = TrackingEntity.GAME
        val id = 1L

        trackingService.trackViewEvent(entity, id)

        verify(trackingRepository).save(captor.capture())
        val capturedEvent = captor.value
        assertThat(entity).isEqualTo(capturedEvent.trackingEntity)
        assertThat(id).isEqualTo(capturedEvent.entityId)
    }

}

