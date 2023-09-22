package com.unqueam.gamingplatform.core.domain

enum class Role(role: String) {

    ADMIN("admin"),
    USER("user"),
    DEVELOPER("developer");

    private val role: String = role
}
