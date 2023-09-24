package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.dtos.BecomeDeveloperRequest
import com.unqueam.gamingplatform.core.domain.RequestToBeDeveloper
import com.unqueam.gamingplatform.core.domain.RequestToBeDeveloperStatus
import com.unqueam.gamingplatform.core.exceptions.ARequestToBeDeveloperIsAlreadyInProcessException
import com.unqueam.gamingplatform.core.mapper.RequestToBeDeveloperMapper
import com.unqueam.gamingplatform.core.services.IDeveloperService
import com.unqueam.gamingplatform.infrastructure.persistence.RequestToBeDeveloperRepository
import com.unqueam.gamingplatform.utils.UserTestResource
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.*

class DeveloperServiceTest {

    private lateinit var developerService: IDeveloperService
    private lateinit var requestToBeDeveloperRepository: RequestToBeDeveloperRepository

    @BeforeEach
    fun setup() {
        requestToBeDeveloperRepository = Mockito.mock(RequestToBeDeveloperRepository::class.java)
        developerService = DeveloperService(requestToBeDeveloperRepository, RequestToBeDeveloperMapper())
    }

    @Test
    fun `a user can make a request to become a developer`() {
        // Arrange
        val user = UserTestResource.buildUser()
        val request = BecomeDeveloperRequest("reason")

        // - Define argument captor: set ID replacing null by value to request domain object
        val captor = ArgumentCaptor.forClass(RequestToBeDeveloper::class.java)
        doAnswer { invocation ->
            val capturedRequest = invocation.getArgument(0) as RequestToBeDeveloper
            capturedRequest.id = 1
            null // Devolver null ya que save generalmente retorna void (Unit)
        }.`when`(requestToBeDeveloperRepository).save(captor.capture())

        `when`(requestToBeDeveloperRepository.existsByIssuerAndRequestStatus(user, RequestToBeDeveloperStatus.PENDING))
            .thenReturn(false)

        // Act
        val response = developerService.becomeDeveloper(request, user)

        // Assert
        assertThat(response.issuerId).isEqualTo(user.id)
        assertThat(response.status).isEqualTo(RequestToBeDeveloperStatus.PENDING)
        verify(requestToBeDeveloperRepository, atMostOnce()).save(any(RequestToBeDeveloper::class.java))
    }

    @Test
    fun `a user cannot make a request to become a developer if he has a request in process`() {
        // Arrange
        val user = UserTestResource.buildUser()
        val request = BecomeDeveloperRequest("reason")

        `when`(requestToBeDeveloperRepository.existsByIssuerAndRequestStatus(user, RequestToBeDeveloperStatus.PENDING))
            .thenReturn(true)

        // Act & Assert
        Assertions.assertThatThrownBy { developerService.becomeDeveloper(request, user) }
            .isInstanceOf(ARequestToBeDeveloperIsAlreadyInProcessException::class.java)
            .hasMessage("Ya tienes una solicitud en curso para convertirte en Desarrollador.")
    }
}