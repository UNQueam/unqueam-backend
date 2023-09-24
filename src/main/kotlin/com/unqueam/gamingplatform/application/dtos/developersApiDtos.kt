package com.unqueam.gamingplatform.application.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.unqueam.gamingplatform.core.domain.RequestToBeDeveloperStatus
import java.time.LocalDateTime

data class BecomeDeveloperRequest(
    @JsonProperty ("reason_to_be_developer")
    val reasonToBeDeveloper: String
)

data class BecomeDeveloperOutput(
    @JsonProperty ("request_id")
    val requestId: Long,
    @JsonProperty ("issuer_id")
    val issuerId: Long,
    @JsonProperty ("success_message")
    val successMessage: String,
    @JsonProperty ("timestamp")
    val timeStamp: LocalDateTime,
    val status: RequestToBeDeveloperStatus
)