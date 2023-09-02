package com.unqueam.gamingplatform.utils

import com.unqueam.gamingplatform.core.domain.Game
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
            listOf(),
            listOf()
        )
    }

}