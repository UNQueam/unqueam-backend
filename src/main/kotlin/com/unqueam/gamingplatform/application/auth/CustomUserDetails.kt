package com.unqueam.gamingplatform.application.auth

import com.unqueam.gamingplatform.core.domain.PlatformUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(private val platformUser: PlatformUser) : UserDetails {

    override fun getAuthorities(): MutableCollection<GrantedAuthority> {
        val role = platformUser.getRole()
        return mutableListOf(SimpleGrantedAuthority("ROLE_$role"))
    }

    override fun getPassword(): String = platformUser.getPassword()

    override fun getUsername(): String = platformUser.getUsername()

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    fun getUser(): PlatformUser = platformUser
}