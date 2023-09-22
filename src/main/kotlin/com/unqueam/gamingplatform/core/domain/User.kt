package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.*

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

    constructor(id: Long?, username: String, password: String, email: String, role: Role) {
        this.id = id
        this.username = username
        this.password = password
        this.email = email
        this.role = role
    }

    fun getRole(): Role = role
    fun getPassword(): String = password
    fun getUsername(): String = username
    fun getEmail(): String = email
}