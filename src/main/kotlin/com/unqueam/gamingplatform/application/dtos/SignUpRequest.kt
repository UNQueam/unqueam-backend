package com.unqueam.gamingplatform.application.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class SignUpRequest(
        val username: String,
        val email: String,
        val password: String,
)