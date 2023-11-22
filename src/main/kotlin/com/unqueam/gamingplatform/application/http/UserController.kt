package com.unqueam.gamingplatform.application.http

import com.unqueam.gamingplatform.core.mapper.UserMapper
import com.unqueam.gamingplatform.core.services.IUserService
import org.apache.catalina.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(API.ENDPOINT_USERS)
class UserController {
    private lateinit var userService: IUserService
    private lateinit var userMapper: UserMapper

    @Autowired
    constructor(userService: IUserService,userMapper: UserMapper) {
        this.userService = userService
        this.userMapper = userMapper
    }

    @GetMapping("/{userId}")
    fun findBannerById(@PathVariable userId: Long) : ResponseEntity<Any> {
        val output = userService.findUserById(userId)
        return ResponseEntity.ok(this.userMapper.mapToOutput(output))
    }
}