package com.unqueam.gamingplatform.core.domain

enum class Role(role: String) {

    ADMIN("Admin"),
    USER("User"),
    DEVELOPER("Developer");

    val role: String = role
}
