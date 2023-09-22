package com.unqueam.gamingplatform.infrastructure.persistence

import com.unqueam.gamingplatform.core.domain.Game

data class GameAndViewsRow(val game: Game, val views: Long)