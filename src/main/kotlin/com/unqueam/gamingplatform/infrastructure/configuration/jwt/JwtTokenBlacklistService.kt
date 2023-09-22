package com.unqueam.gamingplatform.infrastructure.configuration.jwt

import com.unqueam.gamingplatform.infrastructure.configuration.jwt.JwtHelper.getHeader
import com.unqueam.gamingplatform.infrastructure.configuration.jwt.JwtHelper.getJwtTokenFromHeader
import com.unqueam.gamingplatform.infrastructure.configuration.jwt.JwtHelper.isPresentAndIsValid
import jakarta.servlet.http.HttpServletRequest
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class JwtTokenBlacklistService(private val jwtService: JwtService) {

    // TODO: Refactor - implement in memory cache or Redis with TTL.
    private var blacklist: MutableSet<String> = mutableSetOf()

    fun addTokenToBlacklistFromRequest(request: HttpServletRequest) {
        val authHeaderValue: String? = getHeader(request)

        if (isPresentAndIsValid(authHeaderValue)) {
            blacklist.add(getJwtTokenFromHeader(authHeaderValue!!))
        }
    }

    fun isBlacklisted(token: String): Boolean {
        return blacklist.contains(getJwtTokenFromHeader(token))
    }

    /**
     * Cada 25 minutos reseteamos la lista de tokens bloqueados.
     * Esto se debe a que, cada 25 minutos, caduca el token entonces ya luego no seria valido.
     */
    @Scheduled(fixedRate = JwtHelper.TOKEN_TTL_MS.toLong()) // 25 minutos en milisegundos
    fun resetBlacklist() {
        blacklist = HashSet()
    }
}