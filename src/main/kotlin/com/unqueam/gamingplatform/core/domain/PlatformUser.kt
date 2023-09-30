package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table (name = "platform_user")
class PlatformUser {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    val id: Long?
    private val username: String
    private val password: String
    private val email: String
    @Enumerated (EnumType.STRING)
    private var role: Role
    private val createdAt: LocalDateTime

    constructor(id: Long?, username: String, password: String, email: String, role: Role) {
        this.id = id
        this.username = username
        this.password = password
        this.email = email
        this.role = role
        this.createdAt = LocalDateTime.now()

    }

    fun getRole(): Role = role
    fun getPassword(): String = password
    fun getUsername(): String = username
    fun getEmail(): String = email
    fun getCreatedAt(): LocalDateTime = createdAt

    fun changeRoleTo(anotherRole: Role) {
        role = anotherRole
    }
}