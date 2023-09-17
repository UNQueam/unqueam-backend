package com.unqueam.gamingplatform.application.http

import com.unqueam.gamingplatform.application.dtos.AuthenticationOutput
import com.unqueam.gamingplatform.application.dtos.SignInRequest
import com.unqueam.gamingplatform.application.dtos.SignUpRequest
import com.unqueam.gamingplatform.core.services.IAuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(API.ENDPOINT_AUTH)
class AuthController {

    private final val authService: IAuthenticationService

    @Autowired
    constructor(authService: IAuthenticationService) {
        this.authService = authService
    }

    @PostMapping ("/signIn")
    fun userSignIn(@RequestBody signInRequest: SignInRequest) : ResponseEntity<AuthenticationOutput> {
        val output = authService.signIn(signInRequest)
        return ResponseEntity.status(HttpStatus.OK).body(output)
    }

    @PostMapping ("/signUp")
    fun userSignUp(@RequestBody signUpRequest: SignUpRequest) : ResponseEntity<AuthenticationOutput> {
        val output = authService.signUp(signUpRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(output)
    }
}