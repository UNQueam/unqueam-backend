package com.unqueam.gamingplatform.application.exception

import jakarta.servlet.http.HttpServletRequest
import org.apache.commons.lang3.StringUtils
import org.springframework.http.HttpStatus
import java.lang.Exception
import java.time.LocalDateTime

open class ErrorAPIResponse {

    private val httpStatus: HttpStatus
    private val exception: Exception
    private val httpRequest: HttpServletRequest
    private val errorsMap: Map<String, Any>

    constructor(httpStatus: HttpStatus, exception: Exception, httpRequest: HttpServletRequest, errorsMap: Map<String, Any> = mapOf()) {
        this.httpStatus = httpStatus
        this.exception = exception
        this.httpRequest = httpRequest
        this.errorsMap = errorsMap
    }

    open fun buildBody(): Map<String, Any> {
        return mutableMapOf(
            Pair("status_code", httpStatus.value()),
            Pair("status", httpStatus.name),
            Pair("message", exception.message ?: StringUtils.EMPTY),
            Pair("timestamp", LocalDateTime.now().toString()),
            Pair("errors", errorsMap),
            Pair("multiple_errors", errorsMap.isNotEmpty()),
            Pair("path", httpRequest.requestURI)
        )
    }
}
