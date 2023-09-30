package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class RequestToBeDeveloper {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    var id: Long? = null
    @ManyToOne
    private val issuer: PlatformUser
    private val dateTime: LocalDateTime
    @Enumerated (EnumType.STRING)
    private var requestStatus: RequestToBeDeveloperStatus
    private val reason: String
    private var rejectionReason: String

    constructor(id: Long?, issuer: PlatformUser, dateTime: LocalDateTime, requestStatus: RequestToBeDeveloperStatus, reason: String, rejectionReason: String) {
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

    fun approve() {
        requestStatus = RequestToBeDeveloperStatus.APPROVED
        issuer.changeRoleTo(Role.DEVELOPER)
    }

    fun rejectWithReason(rejectionMessage: String) {
        requestStatus = RequestToBeDeveloperStatus.REJECTED
        rejectionReason = rejectionMessage
    }
}