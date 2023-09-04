package com.unqueam.gamingplatform.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class GameBuilderTest {

    @Test
    fun `when developers, images or genres are null, their values are an empty set`() {
        val game: Game = Game.builder()
            .named("name")
            .withLogoUrl("logo")
            .withLinkToGame("link")
            .describedAs("description")
            .releasedAt(LocalDate.now())
            .build()

        assertThat(game.developers).isEmpty()
        assertThat(game.genres).isEmpty()
        assertThat(game.images).isEmpty()
    }

    @Test
    fun `build game`() {
        val releaseDate: LocalDate = LocalDate.now()
        val game: Game = Game.builder()
            .withLinkToGame("link")
            .withLogoUrl("logo")
            .withImages(setOf())
            .releasedAt(releaseDate)
            .withGenres(setOf())
            .describedAs("description")
            .developedBy(setOf())
            .named("name")
            .build()

        assertThat(game.developers).isEmpty()
        assertThat(game.genres).isEmpty()
        assertThat(game.images).isEmpty()
        assertThat(game.name).isEqualTo("name")
        assertThat(game.description).isEqualTo("description")
        assertThat(game.releaseDate).isEqualTo(releaseDate)
        assertThat(game.logoUrl).isEqualTo("logo")
        assertThat(game.linkToGame).isEqualTo("link")
    }
}