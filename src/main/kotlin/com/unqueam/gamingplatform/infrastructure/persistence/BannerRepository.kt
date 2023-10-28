package com.unqueam.gamingplatform.infrastructure.persistence

import com.unqueam.gamingplatform.core.domain.Banner
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface BannerRepository : JpaRepository<Banner, Long> {

    fun existsByAlias(alias: String): Boolean
    fun findByAlias(alias: String): Optional<Banner>
}