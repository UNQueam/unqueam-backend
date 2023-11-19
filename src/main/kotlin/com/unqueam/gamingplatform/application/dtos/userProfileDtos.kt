package com.unqueam.gamingplatform.application.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class UserProfileOutput (
        @JsonProperty("profile_id")
        val profileId: Long,
        val description: String?,
        @JsonProperty("image_id")
        val imageId: String?,
)