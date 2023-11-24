package com.unqueam.gamingplatform.utils

import com.unqueam.gamingplatform.core.domain.Role
import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.core.domain.UserProfile

object UserTestResource {

    fun buildUser(username: String = "username"): PlatformUser {
        val platformUser = PlatformUser(
            1,
            username,
            "Password",
            "user@gmail.com",
            Role.USER)
        val profile = UserProfile(1, platformUser, "", "")
        platformUser.userProfile = profile
        return platformUser
    }

}