package com.unqueam.gamingplatform.application.exception

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.servlet.http.HttpServletRequest
import org.apache.commons.lang3.StringUtils
import org.springframework.http.HttpStatus
import java.lang.Exception
import java.time.LocalDateTime

class ErrorAPIResponse {

    @JsonProperty ()
    val timeStamp: String
    val statusCode: Int
    val status: String
    val message: String
    val path: String

    constructor(httpStatus: HttpStatus, exception: Exception, httpRequest: HttpServletRequest) {
        this.statusCode = httpStatus.value()
        this.status = httpStatus.name
        this.message = exception.message ?: StringUtils.EMPTY
        this.timeStamp = LocalDateTime.now().toString()
        this.path = httpRequest.requestURI
    }
}
