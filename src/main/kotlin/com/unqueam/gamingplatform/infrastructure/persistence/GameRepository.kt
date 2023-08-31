package com.unqueam.gamingplatform.infrastructure.persistence

import com.unqueam.gamingplatform.core.domain.Game
import org.springframework.data.jpa.repository.JpaRepository

interface GameRepository : JpaRepository<Game, Long> {
}