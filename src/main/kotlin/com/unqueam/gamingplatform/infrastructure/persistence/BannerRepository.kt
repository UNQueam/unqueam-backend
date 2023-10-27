package com.unqueam.gamingplatform.infrastructure.persistence

import com.unqueam.gamingplatform.core.domain.Banner
import org.springframework.data.jpa.repository.JpaRepository

interface BannerRepository : JpaRepository<Banner, Long>