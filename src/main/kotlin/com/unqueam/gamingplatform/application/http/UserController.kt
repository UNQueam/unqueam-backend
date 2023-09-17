package com.unqueam.gamingplatform.application.http

import com.unqueam.gamingplatform.application.dtos.GameOutput
import com.unqueam.gamingplatform.application.dtos.UserOutput
import com.unqueam.gamingplatform.core.services.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(API.ENDPOINT_USERS)
class UserController {

    private final val userService: IUserService

    @Autowired
    constructor(userService: IUserService) {
        this.userService = userService
    }

    @GetMapping
    fun fetchUsers() : ResponseEntity<List<UserOutput>> {
        val users: List<UserOutput> = userService.findAllUsers()
        return ResponseEntity.status(HttpStatus.OK).body(users)
    }
}
