package com.unqueam.gamingplatform.infrastructure.configuration

import com.unqueam.gamingplatform.application.auth.CustomUserDetailsService
import com.unqueam.gamingplatform.application.auth.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.commons.lang3.StringUtils.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

private const val BEARER_PREFIX = "Bearer "
private const val AUTH_HEADER_KEY = "Authorization"

@Component
class JwtAuthenticationFilter(private val jwtService: JwtService, private val userService: CustomUserDetailsService) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        val authHeaderValue: String? = request.getHeader(AUTH_HEADER_KEY)

        if (isNotPresentOrIsInvalid(authHeaderValue)) {
            filterChain.doFilter(request, response)
            return
        }

        val jwt: String = getJwtTokenFromHeader(authHeaderValue!!)
        val userEmail: String = jwtService.extractUserName(jwt)

        if (isNotEmpty(userEmail) && thereAreNoAuthenticatedUser()) {
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

    private fun getJwtTokenFromHeader(authHeaderValue: String): String {
        return authHeaderValue.substring(7)
    }

    private fun isNotPresentOrIsInvalid(headerValue: String?): Boolean {
        return isEmpty(headerValue) || !startsWith(headerValue, BEARER_PREFIX)
    }
}