package com.unqueam.gamingplatform.application.http

import com.unqueam.gamingplatform.core.services.IProfileService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserProfileController {

    private final val userProfileService: IProfileService

    constructor(userProfileService: IProfileService) {
        this.userProfileService = userProfileService
    }
    @GetMapping("/api/users/{userId}/profile")
    fun fetchUserProfile(@PathVariable userId: Long): ResponseEntity<Any> {
        val userProfile = userProfileService.findProfileByUserId(userId)
        return ResponseEntity.ok(userProfile)
    }
}