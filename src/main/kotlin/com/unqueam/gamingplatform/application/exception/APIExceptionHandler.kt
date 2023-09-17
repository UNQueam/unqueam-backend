package com.unqueam.gamingplatform.application.exception

import jakarta.persistence.EntityNotFoundException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class APIExceptionHandler {

    @ExceptionHandler(RuntimeException::class)
    fun handleAllUncaughtException(exception: RuntimeException, httpRequest: HttpServletRequest): ResponseEntity<ErrorAPIResponse> {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception, httpRequest)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFound(exception: EntityNotFoundException, httpRequest: HttpServletRequest): ResponseEntity<ErrorAPIResponse> {
        return buildErrorResponse(HttpStatus.NOT_FOUND, exception, httpRequest)
    }

    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUsernameNotFoundException(exception: UsernameNotFoundException, httpRequest: HttpServletRequest): ResponseEntity<ErrorAPIResponse> {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exception, httpRequest)
    }

    private fun buildErrorResponse(httpStatus: HttpStatus, exception: RuntimeException, httpRequest: HttpServletRequest) : ResponseEntity<ErrorAPIResponse> {
        return ResponseEntity
            .status(httpStatus)
            .contentType(MediaType.APPLICATION_JSON)
            .body(ErrorAPIResponse(httpStatus, exception, httpRequest))
    }
}

