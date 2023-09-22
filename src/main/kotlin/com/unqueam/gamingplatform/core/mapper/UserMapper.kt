package com.unqueam.gamingplatform.core.mapper

import com.unqueam.gamingplatform.application.dtos.UserOutput
import com.unqueam.gamingplatform.core.domain.User

class UserMapper {

    fun mapToOutput(user: User): UserOutput {
        return UserOutput(
                user.id!!,
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        )
    }
}