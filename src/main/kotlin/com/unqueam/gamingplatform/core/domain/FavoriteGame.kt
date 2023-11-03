package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class FavoriteGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?

    @ManyToOne
    private val game: Game

    @ManyToOne
    private val platformUser: PlatformUser

    constructor(id: Long?, game: Game, platformUser: PlatformUser) {
        this.id = id
        this.game = game
        this.platformUser = platformUser
    }

    fun game() = game

    fun platformUser() = platformUser
}