package com.unqueam.gamingplatform.core.services

import com.unqueam.gamingplatform.application.dtos.UserProfileOutput

interface IProfileService {
    fun findProfileByUserId(userId: Long): UserProfileOutput
}