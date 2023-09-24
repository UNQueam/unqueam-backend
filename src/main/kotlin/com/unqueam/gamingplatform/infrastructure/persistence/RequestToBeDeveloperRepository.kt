package com.unqueam.gamingplatform.infrastructure.persistence

import com.unqueam.gamingplatform.core.domain.RequestToBeDeveloper
import com.unqueam.gamingplatform.core.domain.RequestToBeDeveloperStatus
import com.unqueam.gamingplatform.core.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface RequestToBeDeveloperRepository : JpaRepository<RequestToBeDeveloper, Long> {

    fun existsByIssuerAndRequestStatus(issuer: User, requestStatus: RequestToBeDeveloperStatus): Boolean
}