package com.unqueam.gamingplatform.infrastructure.persistence

import com.unqueam.gamingplatform.core.domain.RequestToBeDeveloper
import com.unqueam.gamingplatform.core.domain.RequestToBeDeveloperStatus
import com.unqueam.gamingplatform.core.domain.PlatformUser
import org.springframework.data.jpa.repository.JpaRepository

interface RequestToBeDeveloperRepository : JpaRepository<RequestToBeDeveloper, Long> {

    fun existsByIssuerAndRequestStatusIn(issuer: PlatformUser, statuses: List<RequestToBeDeveloperStatus>): Boolean
}