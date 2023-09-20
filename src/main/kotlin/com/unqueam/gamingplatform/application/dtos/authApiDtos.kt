package com.unqueam.gamingplatform.application.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.unqueam.gamingplatform.core.domain.Role
import java.time.LocalDateTime

data class SignInRequest (
    val username: String,
    val password: String
)

data class SignUpRequest(
    val username: String,
    val email: String,
    val password: String
)

data class AuthenticationOutput(
    @JsonProperty ("user_id")
    val userId: Long,
    val username: String,
    val role: Role,
    @JsonProperty ("auth_token")
    val authToken: String,
    @JsonProperty ("time_stamp")
    val timeStamp: LocalDateTime
)