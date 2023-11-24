package com.unqueam.gamingplatform.infrastructure.persistence

import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.core.domain.UserProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UserProfileRepository : JpaRepository<UserProfile, Long> {
    @Query("SELECT up FROM UserProfile up WHERE up.platformUser.id = :platformUserId")
    fun findByPlatformUserId(@Param("platformUserId") platformUserId: Long): Optional<UserProfile>
}