package com.unqueam.gamingplatform.infrastructure.configuration.jwt

import com.unqueam.gamingplatform.application.auth.CustomUserDetailsService
import com.unqueam.gamingplatform.infrastructure.configuration.jwt.JwtHelper.isPresentAndIsValid
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.commons.lang3.StringUtils
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userService: CustomUserDetailsService,
    private val jwtTokenBlacklistService: JwtTokenBlacklistService
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        val authHeaderValue: String? = JwtHelper.getHeader(request)

        if (isNotPresentOrIsInvalid(authHeaderValue)) {
            filterChain.doFilter(request, response)
            return
        }

        val jwt: String = JwtHelper.getJwtTokenFromHeader(authHeaderValue!!)
        val userEmail: String = jwtService.extractUserName(jwt)

        if (StringUtils.isNotEmpty(userEmail) && thereAreNoAuthenticatedUser()) {
            val userDetails = userService.loadUserByUsername(userEmail)
            if (jwtService.isTokenValid(jwt, userDetails)) {
                loadAuthenticatedUserByRequest(request, userDetails)
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun loadAuthenticatedUserByRequest(request: HttpServletRequest, userDetails: UserDetails) {
        val context: SecurityContext = SecurityContextHolder.createEmptyContext()
        val authToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        context.authentication = authToken
        SecurityContextHolder.setContext(context)
    }

    private fun thereAreNoAuthenticatedUser(): Boolean {
        return SecurityContextHolder.getContext().authentication == null
    }

    private fun isNotPresentOrIsInvalid(headerValue: String?): Boolean {
        return !isPresentAndIsValid(headerValue) || jwtTokenBlacklistService.isBlacklisted(headerValue!!)
    }
}