package com.unqueam.gamingplatform.application.http

import com.unqueam.gamingplatform.application.dtos.*
import com.unqueam.gamingplatform.core.services.IAdminService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(API.ENDPOINT_USERS)
class AdminController {

    private final val adminService: IAdminService

    @Autowired
    constructor(anAdminService: IAdminService) {
        this.adminService = anAdminService
    }

    @GetMapping
    fun fetchUsers(): ResponseEntity<List<UserOutput>> {
        val users: List<UserOutput> = adminService.fetchUsers()
        return ResponseEntity.status(HttpStatus.OK).body(users)
    }
}