package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.dtos.UserProfileOutput
import com.unqueam.gamingplatform.application.dtos.UserProfileRequest
import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.core.domain.UserProfile
import com.unqueam.gamingplatform.core.exceptions.Exceptions
import com.unqueam.gamingplatform.core.exceptions.UserHasNotPermissionException
import com.unqueam.gamingplatform.core.mapper.ProfileMapper
import com.unqueam.gamingplatform.core.services.IProfileService
import com.unqueam.gamingplatform.infrastructure.persistence.UserProfileRepository
import jakarta.persistence.EntityNotFoundException
import java.util.*


class ProfileService : IProfileService {

    private val profileRepository: UserProfileRepository
    private val profileMapper: ProfileMapper

    constructor(profileRepository: UserProfileRepository, profileMapper: ProfileMapper) {
        this.profileRepository = profileRepository
        this.profileMapper = profileMapper
    }

    override fun findProfileByUserId(userId: Long): UserProfileOutput {
        return profileMapper.mapToOutput(searchByUserId(userId))
    }

    override fun updateProfileByUserId(userId: Long, userProfileRequest: UserProfileRequest, user: PlatformUser): UserProfileOutput {
        val profile = searchByUserId(userId)

        validateIfIsProfileOwner(userId, user)

        profile.description = userProfileRequest.description
        return profileMapper.mapToOutput(profileRepository.save(profile))
    }

    override fun updateProfileAvatarByUserId(userId: Long, avatarKey: String, user: PlatformUser): UserProfileOutput {
        val profile = searchByUserId(userId)

        validateIfIsProfileOwner(userId, user)

        profile.imageId = avatarKey
        return profileMapper.mapToOutput(profileRepository.save(profile))
    }

    private fun validateIfIsProfileOwner(userId: Long, user: PlatformUser) {
        if (userId != user.id!!) {
            throw UserHasNotPermissionException()
        }
    }


    private fun searchByUserId(userId: Long): UserProfile {
        return profileRepository
                .findByPlatformUserId(userId)
                .orElseThrow { EntityNotFoundException(Exceptions.NOT_EXISTS_PROFILE_WITH_USER_ID.format(userId)) }
    }


}