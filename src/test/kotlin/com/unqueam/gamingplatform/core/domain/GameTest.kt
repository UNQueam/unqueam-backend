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

    @Test
    fun `a game should update its rank when the given views is higher than the needed to move up the rank`() {
        val game = GameTestResource.buildGameWithId(1L)

        val gameHasChanged = game.checkAndUpdateRankIfMeetsTheRequirements(50)

        assertThat(gameHasChanged).isTrue()
        assertThat(game.rankBadge).isEqualTo(RankBadge.RANK_2)
    }

    @Test
    fun `a game should not update its rank when the given views is lower than the needed to move up the rank`() {
        val game = GameTestResource.buildGameWithId(1L)

        val gameHasChanged = game.checkAndUpdateRankIfMeetsTheRequirements(10)

        assertThat(gameHasChanged).isFalse()
        assertThat(game.rankBadge).isEqualTo(RankBadge.RANK_3)
    }
}
