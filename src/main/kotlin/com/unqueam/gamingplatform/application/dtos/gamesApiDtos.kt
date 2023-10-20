package com.unqueam.gamingplatform.application.dtos

import com.fasterxml.jackson.annotation.JsonFormat
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    val releaseDate: LocalDate,
    val developers: Set<DeveloperGameInput> = setOf(),
    val images: Set<GameImageInput> = setOf(),
    val genres: Set<GenreInput> = setOf(),
    @JsonProperty (value = "development_team")
    val developmentTeam: String,
    @JsonProperty (value = "is_hidden")
    val isHidden: Boolean?
)

data class DeveloperGameInput(
    @JsonProperty (value = "name")
    val name: String,
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
    val publisher: PublisherOutput,
    @JsonProperty (value = "is_hidden")
    val isHidden: Boolean,
    val comments: Set<CommentOutput>
)

data class PublisherOutput(
    @JsonProperty("publisher_id")
    val publisherId: Long,
    val username: String
)

data class DeveloperGameOutput(
    val id: Long,
    @JsonProperty (value = "name")
    val name: String,
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

data class CommentInput (
        val rating: Int,
        val content: String
)

data class CommentOutput (
        @JsonProperty("comment_id")
        val commentId: Long,
        @JsonProperty("game_id")
        val gameId: Long,
        val publisher: PublisherOutput,
        val rating: Int,
        val content: String,
        @JsonProperty("creation_date")
        val creationDate: LocalDate,
        @JsonProperty("last_modification")
        val lastModification: LocalDate
)