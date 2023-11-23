package com.unqueam.gamingplatform.application.http

import com.unqueam.gamingplatform.application.auth.AuthContextHelper
import com.unqueam.gamingplatform.application.dtos.UserProfileRequest
import com.unqueam.gamingplatform.core.services.IProfileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    @PutMapping("/api/users/{userId}/profile")
    fun updateUserProfile(@PathVariable userId: Long, @RequestBody userProfileRequest: UserProfileRequest): ResponseEntity<Any> {
        val authenticatedUser = AuthContextHelper.getAuthenticatedUser()
        val output = userProfileService.updateProfileByUserId(userId, userProfileRequest, authenticatedUser)
        return ResponseEntity.status(HttpStatus.OK).body(output)
    }

    @PutMapping("/api/users/{userId}/profile/{avatarKey}")
    fun updateUserProfileAvatar(@PathVariable userId: Long, @PathVariable avatarKey: String): ResponseEntity<Any> {
        val authenticatedUser = AuthContextHelper.getAuthenticatedUser()
        val output = userProfileService.updateProfileAvatarByUserId(userId, avatarKey, authenticatedUser)
        return ResponseEntity.status(HttpStatus.OK).body(output)
    }
}