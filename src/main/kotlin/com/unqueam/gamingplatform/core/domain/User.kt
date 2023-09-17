package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    val id: Long?
    private val username: String
    private val password: String
    private val email: String
    @Enumerated (EnumType.STRING)
    private val role: Role
    private val registrationDate: LocalDate
    val isBanned: Boolean

    constructor(id: Long?, username: String, password: String, email: String, role: Role, registrationDate: LocalDate) {
        this.id = id
        this.username = username
        this.password = password
        this.email = email
        this.role = role
        this.registrationDate = registrationDate
        this.isBanned = false
    }

    fun getRole(): Role = role
    fun getPassword(): String = password
    fun getUsername(): String = username
    fun getEmail(): String = email
    fun getRegistrationDate() = registrationDate
}