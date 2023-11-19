package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.dtos.UserProfileOutput
import com.unqueam.gamingplatform.core.exceptions.Exceptions
import com.unqueam.gamingplatform.core.mapper.ProfileMapper
import com.unqueam.gamingplatform.core.services.IProfileService
import com.unqueam.gamingplatform.infrastructure.persistence.UserProfileRepository
import jakarta.persistence.EntityNotFoundException


class ProfileService : IProfileService {

    private val profileRepository: UserProfileRepository
    private val profileMapper: ProfileMapper

    constructor(profileRepository: UserProfileRepository, profileMapper: ProfileMapper) {
        this.profileRepository = profileRepository
        this.profileMapper = profileMapper
    }

    override fun findProfileByUserId(userId: Long): UserProfileOutput {
        val profile = profileRepository
                .findByPlatformUserId(userId)
                .orElseThrow { EntityNotFoundException(Exceptions.NOT_EXISTS_PROFILE_WITH_USER_ID.format(userId)) }
        return profileMapper.mapToOutput(profile)
    }


}