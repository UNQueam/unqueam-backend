package com.unqueam.gamingplatform.application.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.unqueam.gamingplatform.core.domain.Role
import java.time.LocalDateTime

data class UserOutput(
        @JsonProperty("user_id")
        val userId: Long,
        val username: String,
        val email: String,
        val role: Role,
        @JsonProperty("created_at")
        val createdAt: LocalDateTime
)

data class RejectedMessage(
        val reason: String
)