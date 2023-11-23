package com.unqueam.gamingplatform.application.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.unqueam.gamingplatform.core.domain.Role
import com.unqueam.gamingplatform.infrastructure.configuration.jwt.JwtHelper.TOKEN_TTL_MS
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
    @JsonProperty("profile_image_id")
    val profileImageId: String?,
    val role: Role,
    @JsonProperty ("auth_token")
    val authToken: String,
    @JsonProperty ("time_stamp")
    val timeStamp: LocalDateTime,
    @JsonProperty ("token_ttl_ms")
    val tokenTtlMs: Int = TOKEN_TTL_MS
)