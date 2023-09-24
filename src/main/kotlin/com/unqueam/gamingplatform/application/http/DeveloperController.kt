package com.unqueam.gamingplatform.application.http

import com.unqueam.gamingplatform.application.auth.CustomUserDetails
import com.unqueam.gamingplatform.application.dtos.BecomeDeveloperOutput
import com.unqueam.gamingplatform.application.dtos.BecomeDeveloperRequest
import com.unqueam.gamingplatform.core.services.IDeveloperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(API.ENDPOINT_DEVELOPERS)
class DeveloperController {

    private final val developerService: IDeveloperService

    @Autowired
    constructor(developerService: IDeveloperService) {
        this.developerService = developerService
    }

    @PostMapping
    fun becomeDeveloper(@RequestBody becomeDeveloperRequest: BecomeDeveloperRequest, authentication: Authentication) : ResponseEntity<BecomeDeveloperOutput> {
        val authenticatedUser = (authentication.principal as CustomUserDetails).getUser()
        val output = developerService.becomeDeveloper(becomeDeveloperRequest, authenticatedUser)
        return ResponseEntity.status(HttpStatus.CREATED).body(output)
    }
}