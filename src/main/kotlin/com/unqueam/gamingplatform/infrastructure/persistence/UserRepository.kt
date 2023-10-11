package com.unqueam.gamingplatform.infrastructure.persistence

import com.unqueam.gamingplatform.core.domain.PlatformUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface UserRepository : JpaRepository<PlatformUser, Long> {

    fun findByUsername(username: String): Optional<PlatformUser>
    fun findByUsernameOrEmail(username: String, email: String): Optional<PlatformUser>

    @Query("SELECT u.email FROM PlatformUser u WHERE u.role = 'ADMIN'")
    fun findAdminEmails(): List<String>
}