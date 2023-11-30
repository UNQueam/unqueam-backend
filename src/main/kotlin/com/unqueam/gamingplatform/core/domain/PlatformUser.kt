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
    @OneToMany (fetch = FetchType.LAZY)
    private val publishedGames: List<Game>
    @OneToMany (fetch = FetchType.LAZY)
    private val comments: List<Comment>
    @OneToMany (fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    private val favoritesGames: MutableList<FavoriteGame>
    @OneToOne (fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var userProfile: UserProfile? = null

    constructor(id: Long?, username: String, password: String, email: String, role: Role, publishedGames: List<Game> = mutableListOf(),
    comments: List<Comment> = mutableListOf(), favoritesGames: List<FavoriteGame> = mutableListOf()) {
        this.id = id
        this.username = username
        this.password = password
        this.email = email
        this.role = role
        this.createdAt = LocalDateTime.now()
        this.publishedGames = publishedGames
        this.comments = comments
        this.favoritesGames = favoritesGames.toMutableList()
        this.userProfile = UserProfile(null, this)
    }

    fun getRole(): Role = role
    fun getPassword(): String = password
    fun getUsername(): String = username
    fun getEmail(): String = email
    fun getCreatedAt(): LocalDateTime = createdAt
    fun hasRole(role: Role): Boolean = this.role == role

    fun setProfile(profile: UserProfile) {
        this.userProfile = profile
    }
    fun getProfile(): UserProfile = userProfile!!

    fun changeRoleTo(anotherRole: Role) {
        role = anotherRole
    }
}