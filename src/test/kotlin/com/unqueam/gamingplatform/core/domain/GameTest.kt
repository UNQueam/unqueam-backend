package com.unqueam.gamingplatform.core.domain

import com.unqueam.gamingplatform.utils.GameTestResource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GameTest {

    @Test
    fun `when a game is synchronized with another game, it should be updated`() {
        val game = GameTestResource.buildGameWithId(1)

        val anotherGame = GameTestResource.buildGameWithId(2)

        val result = game.syncWith(anotherGame)
        assertThat(result.id).isEqualTo(game.id)
        assertThat(result.name).isEqualTo(anotherGame.name)
    }
}