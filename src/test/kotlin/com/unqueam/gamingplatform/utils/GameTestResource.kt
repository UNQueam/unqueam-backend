package com.unqueam.gamingplatform.utils

import com.unqueam.gamingplatform.core.domain.Game
import com.unqueam.gamingplatform.core.domain.RankBadge
import java.time.LocalDate

object GameTestResource {

    fun buildGameWithId(id: Long): Game {
        return Game(
            id,
            "overwatch",
            "logo.url/",
            "description",
            "link",
            LocalDate.now(),
            setOf(),
            setOf(),
            RankBadge.RANK_3
        )
    }
}