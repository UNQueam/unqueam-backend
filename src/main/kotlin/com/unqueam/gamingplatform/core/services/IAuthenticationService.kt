package com.unqueam.gamingplatform.core.services

import com.unqueam.gamingplatform.application.dtos.AuthenticationOutput
import com.unqueam.gamingplatform.application.dtos.SignInRequest
import com.unqueam.gamingplatform.application.dtos.SignUpRequest

interface IAuthenticationService {

    fun signUp(request: SignUpRequest): AuthenticationOutput
    fun signIn(request: SignInRequest): AuthenticationOutput
}