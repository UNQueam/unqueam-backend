package com.unqueam.gamingplatform.application.auth

import com.unqueam.gamingplatform.core.domain.User
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
        val user: User = userService.findUserByUsername(username)
        return CustomUserDetails(user)
    }
}
