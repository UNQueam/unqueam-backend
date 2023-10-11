package com.unqueam.gamingplatform.application.http

import com.unqueam.gamingplatform.application.auth.AuthContextHelper
import com.unqueam.gamingplatform.application.dtos.BecomeDeveloperOutput
import com.unqueam.gamingplatform.application.dtos.BecomeDeveloperRequest
import com.unqueam.gamingplatform.application.dtos.RejectedMessage
import com.unqueam.gamingplatform.application.dtos.RequestOutput
import com.unqueam.gamingplatform.core.services.IDeveloperRequestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(API.ENDPOINT_REQUESTS)
class DeveloperRequestController {

    private final val developerService: IDeveloperRequestService

    @Autowired
    constructor(developerService: IDeveloperRequestService) {
        this.developerService = developerService
    }

    @GetMapping
    fun fetchRequests(): ResponseEntity<List<RequestOutput>> {
        val request: List<RequestOutput> = developerService.fetchRequests()
        return ResponseEntity.status(HttpStatus.OK).body(request)
    }

    @PostMapping
    fun becomeDeveloper(@RequestBody becomeDeveloperRequest: BecomeDeveloperRequest, authentication: Authentication) : ResponseEntity<BecomeDeveloperOutput> {
        val authenticatedUser = AuthContextHelper.getAuthenticatedUser()
        val output = developerService.becomeDeveloper(becomeDeveloperRequest, authenticatedUser)
        return ResponseEntity.status(HttpStatus.CREATED).body(output)
    }

    @PutMapping ("/{requestId}/reject")
    fun rejectRequest(@PathVariable requestId: Long, @RequestBody rejectedMessage: RejectedMessage) : ResponseEntity<Any> {
        developerService.rejectRequestToBeDeveloperWithId(requestId, rejectedMessage)
        return ResponseEntity.noContent().build()
    }

    @PutMapping ("/{requestId}/approve")
    fun approveRequest(@PathVariable requestId: Long) : ResponseEntity<Any> {
        developerService.approveRequestToBeDeveloperWithId(requestId)
        return ResponseEntity.noContent().build()
    }
}