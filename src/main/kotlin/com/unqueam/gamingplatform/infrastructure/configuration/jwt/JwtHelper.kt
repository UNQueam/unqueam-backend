package com.unqueam.gamingplatform.infrastructure.configuration.jwt

import jakarta.servlet.http.HttpServletRequest
import org.apache.commons.lang3.StringUtils.isNotEmpty
import org.apache.commons.lang3.StringUtils.startsWith

object JwtHelper {

    const val TOKEN_TTL_MS = (25 * 60 * 1000)

    private const val AUTH_HEADER_KEY = "Authorization"
    private const val BEARER_PREFIX = "Bearer "

    fun getJwtTokenFromHeader(authHeaderValue: String): String {
        return authHeaderValue.substring(7)
    }

    fun getHeader(request: HttpServletRequest): String? {
        return request.getHeader(AUTH_HEADER_KEY)
    }

    fun isPresentAndIsValid(authHeaderValue: String?): Boolean {
        return isNotEmpty(authHeaderValue) && startsWith(authHeaderValue, BEARER_PREFIX)
    }
}