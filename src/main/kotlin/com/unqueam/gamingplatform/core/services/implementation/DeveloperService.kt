package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.dtos.BecomeDeveloperOutput
import com.unqueam.gamingplatform.application.dtos.BecomeDeveloperRequest
import com.unqueam.gamingplatform.core.domain.RequestToBeDeveloperStatus
import com.unqueam.gamingplatform.core.domain.User
import com.unqueam.gamingplatform.core.exceptions.ARequestToBeDeveloperIsAlreadyInProcessException
import com.unqueam.gamingplatform.core.mapper.RequestToBeDeveloperMapper
import com.unqueam.gamingplatform.core.services.IDeveloperService
import com.unqueam.gamingplatform.infrastructure.persistence.RequestToBeDeveloperRepository

class DeveloperService : IDeveloperService {

    private val requestToBeDeveloperRepository: RequestToBeDeveloperRepository
    private val requestToBeDeveloperMapper: RequestToBeDeveloperMapper

    constructor(requestToBeDeveloperRepository: RequestToBeDeveloperRepository, requestToBeDeveloperMapper: RequestToBeDeveloperMapper) {
        this.requestToBeDeveloperRepository = requestToBeDeveloperRepository
        this.requestToBeDeveloperMapper = requestToBeDeveloperMapper
    }

    override fun becomeDeveloper(becomeDeveloperRequest: BecomeDeveloperRequest, user: User): BecomeDeveloperOutput {
        validateThatTheUserDoesNotHaveARequestInProgress(user)
        val requestToBeDeveloper = requestToBeDeveloperMapper.mapToInput(becomeDeveloperRequest, user)
        requestToBeDeveloperRepository.save(requestToBeDeveloper)
        return requestToBeDeveloperMapper.mapToOutput(requestToBeDeveloper)
    }

    /**
     * Un usuario no puede enviar una solicitud para convertirse en desarrollador si ya tiene una solicitud PENDIENTE
     */
    private fun validateThatTheUserDoesNotHaveARequestInProgress(user: User) {
        if (requestToBeDeveloperRepository.existsByIssuerAndRequestStatus(user, RequestToBeDeveloperStatus.PENDING))
            throw ARequestToBeDeveloperIsAlreadyInProcessException()
    }
}