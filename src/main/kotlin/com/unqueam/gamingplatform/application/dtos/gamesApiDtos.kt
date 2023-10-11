package com.unqueam.gamingplatform.application.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.unqueam.gamingplatform.core.domain.Role
import java.time.LocalDate
import java.time.LocalDateTime

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

data class GameOutput(
    val id: Long,
    val name: String,
    @JsonProperty (value = "logo_url")
    val logoUrl: String,
    val description: String,
    @JsonProperty (value = "link_to_game")
    val linkToGame: String,
    @JsonProperty (value = "release_date")
    val releaseDate: LocalDate,
    val developers: Set<DeveloperGameOutput>,
    val images: Set<GameImageOutput>,
    val genres: Set<GenreOutput>,
    @JsonProperty (value = "development_team")
    val developmentTeam: String,
    @JsonProperty (value = "rank_badge")
    val rankBadge: String,
    val publisher: PublisherOutput
)

data class PublisherOutput(
    @JsonProperty("publisher_id")
    val publisherId: Long,
    val username: String
)

data class DeveloperGameOutput(
    val id: Long,
    @JsonProperty (value = "first_name")
    val firstName: String,
    @JsonProperty (value = "last_name")
    val lastName: String,
)


data class GameImageOutput(
    val id: Long,
    val url: String
)

data class GenreOutput(
    val enum: String,
    @JsonProperty (value = "spanish_name")
    val spanishName: String,
    @JsonProperty (value = "english_name")
    val englishName: String
)