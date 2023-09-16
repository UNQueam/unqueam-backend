package com.unqueam.gamingplatform.application.auth

import com.unqueam.gamingplatform.application.dtos.SignUpRequest
import com.unqueam.gamingplatform.application.dtos.SigninRequest


interface AuthenticationService {
    fun signup(request: SignUpRequest): String
    fun signin(request: SigninRequest): String
}