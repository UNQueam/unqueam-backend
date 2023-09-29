package com.unqueam.gamingplatform.application.auth

import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.core.services.IUserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService : UserDetailsService {

    private val userService: IUserService

    constructor(userService: IUserService) {
        this.userService = userService
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val platformUser: PlatformUser = userService.findUserByUsername(username)
        return CustomUserDetails(platformUser)
    }
}
