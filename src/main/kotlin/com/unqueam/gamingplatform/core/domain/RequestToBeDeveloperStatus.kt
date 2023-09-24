package com.unqueam.gamingplatform.core.domain

enum class RequestToBeDeveloperStatus {
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected");

    private val status: String

    constructor(status: String) {
        this.status = status
    }
}