package com.unqueam.gamingplatform.application.auth

import com.unqueam.gamingplatform.core.domain.PlatformUser
import org.springframework.security.core.context.SecurityContextHolder

object AuthContextHelper {

    fun getAuthenticatedUser(): PlatformUser {
        return (SecurityContextHolder.getContext().authentication.principal as CustomUserDetails).getUser()
    }
}