package com.unqueam.gamingplatform.infrastructure.configuration.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import kotlin.collections.HashMap

@Service
class JwtServiceImpl : JwtService {

    private val jwtSigningKey : Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    override fun extractUserName(token: String): String {
        return extractClaim<String>(token, Claims::getSubject)
    }

    override fun generateToken(userDetails: UserDetails): String {
        return generateToken(HashMap(), userDetails)
    }

    override fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val userName = extractUserName(token)
        return userName == userDetails.username && !isTokenExpired(token)
    }

    private fun <T> extractClaim(token: String, claimsResolvers: (Claims) -> T): T {
        val claims: Claims = extractAllClaims(token)
        return claimsResolvers(claims)
    }

    private fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String {
        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(generateExpirationDate())
            .signWith(jwtSigningKey)
            .compact()
    }

    private fun generateExpirationDate(): Date? {
        return Date(System.currentTimeMillis() + JwtHelper.TOKEN_TTL_MS)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim<Date>(token, Claims::getExpiration)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(jwtSigningKey)
            .build()
            .parseClaimsJws(token)
            .body
    }
}