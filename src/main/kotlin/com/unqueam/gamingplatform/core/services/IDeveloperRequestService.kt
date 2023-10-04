package com.unqueam.gamingplatform.core.services

import com.unqueam.gamingplatform.application.dtos.BecomeDeveloperOutput
import com.unqueam.gamingplatform.application.dtos.BecomeDeveloperRequest
import com.unqueam.gamingplatform.application.dtos.RejectedMessage
import com.unqueam.gamingplatform.application.dtos.RequestOutput
import com.unqueam.gamingplatform.core.domain.PlatformUser

interface IDeveloperRequestService {

    fun fetchRequests(): List<RequestOutput>
    fun becomeDeveloper(becomeDeveloperRequest: BecomeDeveloperRequest, user: PlatformUser): BecomeDeveloperOutput
    fun rejectRequestToBeDeveloperWithId(requestId: Long, rejectedMessage: RejectedMessage)
    fun approveRequestToBeDeveloperWithId(requestId: Long)
}