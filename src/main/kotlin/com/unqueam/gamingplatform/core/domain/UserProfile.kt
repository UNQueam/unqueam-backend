package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.*

@Entity
class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?
    val description: String?
    val imageId: String?


    @OneToOne
    private val platformUser: PlatformUser

    constructor(id: Long?, platformUser: PlatformUser, description: String? = "", imageId: String= "") {
        this.id = id
        this.platformUser = platformUser
        this.description = description
        this.imageId = imageId
    }

    fun platformUser() = platformUser
}