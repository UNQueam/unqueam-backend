package com.unqueam.gamingplatform.utils

import com.unqueam.gamingplatform.application.dtos.GameOutput
import com.unqueam.gamingplatform.core.domain.Game
import com.unqueam.gamingplatform.core.domain.RankBadge
import com.unqueam.gamingplatform.core.mapper.GameMapper
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
            RankBadge.UNRANKED,
            setOf(),
            "developmentTeam",
            UserTestResource.buildUser()
        )
    }

    fun buildGameOutputWithId(id: Long): GameOutput {
        return GameMapper().mapToOutput(buildGameWithId(id))
    }
}