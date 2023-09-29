package com.unqueam.gamingplatform.core.mapper

import com.unqueam.gamingplatform.application.dtos.UserOutput
import com.unqueam.gamingplatform.core.domain.PlatformUser

class UserMapper {

    fun mapToOutput(user: PlatformUser): UserOutput {
        return UserOutput(
                user.id!!,
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        )
    }
}