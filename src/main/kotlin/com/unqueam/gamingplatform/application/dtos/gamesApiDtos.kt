package com.unqueam.gamingplatform.application.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class GameRequest(
    val name: String,
    @JsonProperty (value = "logo_url")
    val logoUrl: String,
    val description: String,
    @JsonProperty (value = "link_to_game")
    val linkToGame: String,
    @JsonProperty (value = "release_date")
    val releaseDate: LocalDate,
    val developers: Set<DeveloperGameInput>,
    val images: Set<GameImageInput>,
    val genres: Set<GenreInput>,
    @JsonProperty (value = "development_team")
    val developmentTeam: String
)

data class DeveloperGameInput(
    @JsonProperty (value = "first_name")
    val firstName: String,
    @JsonProperty (value = "last_name")
    val lastName: String,
)

data class GameImageInput(
    val url: String
)

data class GenreInput(
    val name: String
)