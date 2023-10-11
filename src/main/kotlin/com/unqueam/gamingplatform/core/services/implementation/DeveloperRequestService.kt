package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.dtos.BecomeDeveloperOutput
import com.unqueam.gamingplatform.application.dtos.BecomeDeveloperRequest
import com.unqueam.gamingplatform.application.dtos.RejectedMessage
import com.unqueam.gamingplatform.application.dtos.RequestOutput
import com.unqueam.gamingplatform.core.constants.EmailTemplates
import com.unqueam.gamingplatform.core.constants.StaticResources
import com.unqueam.gamingplatform.core.constants.Urls
import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.core.domain.RequestToBeDeveloper
import com.unqueam.gamingplatform.core.domain.RequestToBeDeveloperStatus
import com.unqueam.gamingplatform.core.domain.RequestToBeDeveloperStatus.PENDING
import com.unqueam.gamingplatform.core.exceptions.ARequestToBeDeveloperIsAlreadyInProcessException
import com.unqueam.gamingplatform.core.exceptions.CannotChangeStatusOfARequestThatHasAlreadyBeenModifiedException
import com.unqueam.gamingplatform.core.exceptions.Exceptions.REQUEST_TO_BE_DEVELOPER_NOT_FOUND_ERROR_MESSAGE
import com.unqueam.gamingplatform.core.helper.DateTimeHelper.getFormatter
import com.unqueam.gamingplatform.core.mapper.RequestToBeDeveloperMapper
import com.unqueam.gamingplatform.core.services.IDeveloperRequestService
import com.unqueam.gamingplatform.core.services.IEmailService
import com.unqueam.gamingplatform.infrastructure.persistence.RequestToBeDeveloperRepository
import com.unqueam.gamingplatform.infrastructure.persistence.UserRepository
import jakarta.persistence.EntityNotFoundException

class DeveloperRequestService : IDeveloperRequestService {

    private val requestToBeDeveloperRepository: RequestToBeDeveloperRepository
    private val requestToBeDeveloperMapper: RequestToBeDeveloperMapper
    private val userRepository: UserRepository
    private val emailService: IEmailService

    constructor(
        requestToBeDeveloperRepository: RequestToBeDeveloperRepository,
        requestToBeDeveloperMapper: RequestToBeDeveloperMapper,
        userRepository: UserRepository,
        emailService: IEmailService) {
        this.requestToBeDeveloperRepository = requestToBeDeveloperRepository
        this.requestToBeDeveloperMapper = requestToBeDeveloperMapper
        this.userRepository = userRepository
        this.emailService = emailService
    }

    override fun fetchRequests(): List<RequestOutput> {
        return requestToBeDeveloperRepository.findAll().map { requestToBeDeveloperMapper.mapToAdminOutput(it) }
    }

    override fun becomeDeveloper(becomeDeveloperRequest: BecomeDeveloperRequest, user: PlatformUser): BecomeDeveloperOutput {
        validateThatTheUserDoesNotHaveARequestInProgress(user)
        val requestToBeDeveloper = requestToBeDeveloperMapper.mapToInput(becomeDeveloperRequest, user)
        requestToBeDeveloperRepository.save(requestToBeDeveloper)
        sendEmailNotificationToAdministrators(requestToBeDeveloper)
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
        userRepository.save(request.issuer())
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

    /**
     * Send email to all administrators notifying that a new request to be developer was created.
     */
    private fun sendEmailNotificationToAdministrators(requestToBeDeveloper: RequestToBeDeveloper) {
        val issuer = requestToBeDeveloper.getIssuerUsername()
        val adminEmails = userRepository.findAdminEmails().toTypedArray()
        val content = mutableMapOf<String, Any>(
            Pair("username", issuer),
            Pair("time_stamp", requestToBeDeveloper.getDateTime().format(getFormatter())),
            Pair("request_id", requestToBeDeveloper.id!!),
            Pair("reason", requestToBeDeveloper.reason()),
            Pair("unqueam_url_home", Urls.FRONTEND_HOME_URL),
            Pair("unqueam_url_requests", Urls.FRONTEND_ADMIN_REQUESTS_URL)
        )
        val inlineContent = mutableMapOf(Pair("icon", StaticResources.unqueamIcon()))

        emailService.sendEmail(
            subject = "[Admin] Solicitud para ser desarrollador - Usuario: $issuer",
            from = EmailTemplates.ADMIN_EMAIL,
            to = adminEmails,
            content = content,
            templateName = EmailTemplates.NEW_REQUEST_TO_BE_DEVELOPER_TEMPLATE,
            helperInlineContent = inlineContent
        )
    }
}