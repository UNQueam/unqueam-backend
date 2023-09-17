package com.unqueam.gamingplatform.core.services

import com.unqueam.gamingplatform.application.dtos.AuthenticationOutput
import com.unqueam.gamingplatform.application.dtos.SignInRequest
import com.unqueam.gamingplatform.application.dtos.SignUpRequest
import jakarta.servlet.http.HttpServletRequest

interface IAuthenticationService {

    fun signUp(request: SignUpRequest): AuthenticationOutput
    fun signIn(request: SignInRequest, httpRequest: HttpServletRequest): AuthenticationOutput
}