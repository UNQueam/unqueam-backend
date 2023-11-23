package com.unqueam.gamingplatform.core.services

import com.unqueam.gamingplatform.application.dtos.UserProfileOutput
import com.unqueam.gamingplatform.application.dtos.UserProfileRequest
import com.unqueam.gamingplatform.core.domain.PlatformUser

interface IProfileService {
    fun findProfileByUserId(userId: Long): UserProfileOutput
    fun updateProfileByUserId(userId: Long, userProfileRequest: UserProfileRequest, user: PlatformUser): UserProfileOutput
    fun updateProfileAvatarByUserId(userId: Long, avatarKey: String, user: PlatformUser): UserProfileOutput
}