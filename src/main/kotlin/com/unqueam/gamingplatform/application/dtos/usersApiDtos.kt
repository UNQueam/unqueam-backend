package com.unqueam.gamingplatform.application.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class UserOutput(
    val id: Long,
    val username: String,
    val email: String,
    val role: String,
    @JsonProperty ("registration_date")
    val registrationDate: LocalDate,
    @JsonProperty ("is_banned")
    val isBanned: Boolean
)