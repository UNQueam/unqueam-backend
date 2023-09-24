package com.unqueam.gamingplatform.utils

import com.unqueam.gamingplatform.core.domain.Role
import com.unqueam.gamingplatform.core.domain.User

object UserTestResource {

    fun buildUser(): User {
        return User(1, "username", "Password", "user@gmail.com", Role.USER)
    }

}