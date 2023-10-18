package com.unqueam.gamingplatform.application.exception

import com.unqueam.gamingplatform.core.exceptions.ARequestToBeDeveloperIsAlreadyInProcessException
import com.unqueam.gamingplatform.core.exceptions.CannotChangeStatusOfARequestThatHasAlreadyBeenModifiedException
import com.unqueam.gamingplatform.core.exceptions.SignUpFormException
import com.unqueam.gamingplatform.core.exceptions.UserIsNotThePublisherOfTheGameException
import jakarta.persistence.EntityNotFoundException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class APIExceptionHandler {

    @ExceptionHandler(RuntimeException::class)
    fun handleAllUncaughtException(exception: RuntimeException, httpRequest: HttpServletRequest): ResponseEntity<Map<String, Any>> {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception, httpRequest)
    }

    @ExceptionHandler(CannotChangeStatusOfARequestThatHasAlreadyBeenModifiedException::class)
    fun handleChangedRequestToBeDeveloper(exception: CannotChangeStatusOfARequestThatHasAlreadyBeenModifiedException, httpRequest: HttpServletRequest): ResponseEntity<Map<String, Any>> {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exception, httpRequest)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFound(exception: EntityNotFoundException, httpRequest: HttpServletRequest): ResponseEntity<Map<String, Any>> {
        return buildErrorResponse(HttpStatus.NOT_FOUND, exception, httpRequest)
    }

    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUsernameNotFoundException(exception: UsernameNotFoundException, httpRequest: HttpServletRequest): ResponseEntity<Map<String, Any>> {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exception, httpRequest)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(exception: BadCredentialsException, httpRequest: HttpServletRequest): ResponseEntity<Map<String, Any>> {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exception, httpRequest)
    }

    @ExceptionHandler(SignUpFormException::class)
    fun handleSignUpFormException(exception: SignUpFormException, httpRequest: HttpServletRequest): ResponseEntity<Map<String, Any>> {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exception, httpRequest, exception.errorsMap)
    }

    @ExceptionHandler(ARequestToBeDeveloperIsAlreadyInProcessException::class)
    fun handleRequestToBeDeveloperIsAlreadyInProcessException(exception: ARequestToBeDeveloperIsAlreadyInProcessException, httpRequest: HttpServletRequest): ResponseEntity<Map<String, Any>> {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exception, httpRequest)
    }

    @ExceptionHandler(UserIsNotThePublisherOfTheGameException::class)
    fun handleUserIsNotThePublisherOfTheGame(exception: UserIsNotThePublisherOfTheGameException, httpRequest: HttpServletRequest): ResponseEntity<Map<String, Any>> {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, exception, httpRequest)
    }

    private fun buildErrorResponse(httpStatus: HttpStatus, exception: RuntimeException, httpRequest: HttpServletRequest, errorsMap: Map<String, Any> = mapOf()) : ResponseEntity<Map<String, Any>> {
        return ResponseEntity
            .status(httpStatus)
            .contentType(MediaType.APPLICATION_JSON)
            .body(ErrorAPIResponse(httpStatus, exception, httpRequest, errorsMap).buildBody())
    }
}

