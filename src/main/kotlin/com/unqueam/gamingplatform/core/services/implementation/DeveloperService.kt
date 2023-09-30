package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.dtos.BecomeDeveloperOutput
import com.unqueam.gamingplatform.application.dtos.BecomeDeveloperRequest
import com.unqueam.gamingplatform.application.dtos.RejectedMessage
import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.core.domain.RequestToBeDeveloper
import com.unqueam.gamingplatform.core.domain.RequestToBeDeveloperStatus
import com.unqueam.gamingplatform.core.domain.RequestToBeDeveloperStatus.PENDING
import com.unqueam.gamingplatform.core.domain.Role
import com.unqueam.gamingplatform.core.exceptions.ARequestToBeDeveloperIsAlreadyInProcessException
import com.unqueam.gamingplatform.core.exceptions.CannotChangeStatusOfARequestThatHasAlreadyBeenModifiedException
import com.unqueam.gamingplatform.core.exceptions.Exceptions.GAME_NOT_FOUND_ERROR_MESSAGE
import com.unqueam.gamingplatform.core.exceptions.Exceptions.REQUEST_TO_BE_DEVELOPER_NOT_FOUND_ERROR_MESSAGE
import com.unqueam.gamingplatform.core.mapper.RequestToBeDeveloperMapper
import com.unqueam.gamingplatform.core.services.IDeveloperService
import com.unqueam.gamingplatform.infrastructure.persistence.RequestToBeDeveloperRepository
import jakarta.persistence.EntityNotFoundException

class DeveloperService : IDeveloperService {

    private val requestToBeDeveloperRepository: RequestToBeDeveloperRepository
    private val requestToBeDeveloperMapper: RequestToBeDeveloperMapper

    constructor(requestToBeDeveloperRepository: RequestToBeDeveloperRepository, requestToBeDeveloperMapper: RequestToBeDeveloperMapper) {
        this.requestToBeDeveloperRepository = requestToBeDeveloperRepository
        this.requestToBeDeveloperMapper = requestToBeDeveloperMapper
    }

    override fun becomeDeveloper(becomeDeveloperRequest: BecomeDeveloperRequest, user: PlatformUser): BecomeDeveloperOutput {
        validateThatTheUserDoesNotHaveARequestInProgress(user)
        val requestToBeDeveloper = requestToBeDeveloperMapper.mapToInput(becomeDeveloperRequest, user)
        requestToBeDeveloperRepository.save(requestToBeDeveloper)
        return requestToBeDeveloperMapper.mapToOutput(requestToBeDeveloper)
    }

    override fun rejectRequestToBeDeveloperWithId(requestId: Long, rejectedMessage: RejectedMessage) {
        val request: RequestToBeDeveloper = findRequestById(requestId)
        validateThatTheRequestHasNotBeenModifiedToAnotherState(request)

        request.rejectWithReason(rejectedMessage.reason)
        requestToBeDeveloperRepository.save(request)
    }

    override fun approveRequestToBeDeveloperWithId(requestId: Long) {
        val request: RequestToBeDeveloper = findRequestById(requestId)
        validateThatTheRequestHasNotBeenModifiedToAnotherState(request)

        request.approve()
        requestToBeDeveloperRepository.save(request)
    }

    private fun validateThatTheRequestHasNotBeenModifiedToAnotherState(request: RequestToBeDeveloper) {
        if (!request.status().equals(PENDING))
            throw CannotChangeStatusOfARequestThatHasAlreadyBeenModifiedException()
    }

    private fun findRequestById(requestId: Long): RequestToBeDeveloper {
        return requestToBeDeveloperRepository
            .findById(requestId)
            .orElseThrow { EntityNotFoundException(REQUEST_TO_BE_DEVELOPER_NOT_FOUND_ERROR_MESSAGE.format(requestId)) }
    }

    /**
     * Un usuario no puede enviar una solicitud para convertirse en desarrollador si ya tiene una solicitud PENDIENTE
     */
    private fun validateThatTheUserDoesNotHaveARequestInProgress(user: PlatformUser) {
        val inProcessStatuses = listOf(PENDING, RequestToBeDeveloperStatus.APPROVED)
        if (requestToBeDeveloperRepository.existsByIssuerAndRequestStatusIn(user, inProcessStatuses))
            throw ARequestToBeDeveloperIsAlreadyInProcessException()
    }
}