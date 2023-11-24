package com.unqueam.gamingplatform.core.mapper

import com.unqueam.gamingplatform.application.dtos.UserProfileOutput
import com.unqueam.gamingplatform.core.domain.UserProfile

class ProfileMapper {

    fun mapToOutput(profile: UserProfile): UserProfileOutput {
        return UserProfileOutput(
                profileId = profile.id!!,
                description = profile.description,
                imageId = profile.imageId
        )
    }
}