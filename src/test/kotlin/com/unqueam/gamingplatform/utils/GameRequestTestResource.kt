package com.unqueam.gamingplatform.utils

import com.fasterxml.jackson.annotation.JsonProperty
import com.unqueam.gamingplatform.application.dtos.GameRequest
import java.time.LocalDate

object GameRequestTestResource {

    fun buildGameRequest(): GameRequest {
        return GameRequest(
            "game name",
            "url",
            "description",
            "link",
            LocalDate.now(),
            setOf(),
            setOf(),
            setOf(),
            "developmentTeam",
            null
        )
    }

}
