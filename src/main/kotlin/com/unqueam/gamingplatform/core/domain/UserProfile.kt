package com.unqueam.gamingplatform.core.domain

import com.unqueam.gamingplatform.application.dtos.UserProfileRequest
import jakarta.persistence.*

@Entity
class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?
    var description: String?
    var imageId: String?


    @OneToOne
    private val platformUser: PlatformUser

    constructor(id: Long?, platformUser: PlatformUser, description: String? = "", imageId: String?= "") {
        this.id = id
        this.platformUser = platformUser
        this.description = description
        this.imageId = imageId
    }

    fun platformUser() = platformUser
}