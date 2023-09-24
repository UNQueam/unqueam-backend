package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class RequestToBeDeveloper {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    var id: Long? = null
    @ManyToOne
    private val issuer: User
    private val dateTime: LocalDateTime
    @Enumerated (EnumType.STRING)
    private val requestStatus: RequestToBeDeveloperStatus
    private val reason: String
    private val rejectionReason: String

    constructor(id: Long?, issuer: User, dateTime: LocalDateTime, requestStatus: RequestToBeDeveloperStatus, reason: String, rejectionReason: String) {
        this.id = id
        this.issuer = issuer
        this.dateTime = dateTime
        this.requestStatus = requestStatus
        this.reason = reason
        this.rejectionReason = rejectionReason
    }

    fun getId(): Long = id!!
    fun getIssuerId(): Long = issuer.id!!
    fun getDateTime(): LocalDateTime = dateTime
    fun status(): RequestToBeDeveloperStatus = requestStatus
}