package com.unqueam.gamingplatform.utils

import com.unqueam.gamingplatform.core.domain.Role
import com.unqueam.gamingplatform.core.domain.PlatformUser

object UserTestResource {

    fun buildUser(): PlatformUser {
        return PlatformUser(1, "username", "Password", "user@gmail.com", Role.USER)
    }

}