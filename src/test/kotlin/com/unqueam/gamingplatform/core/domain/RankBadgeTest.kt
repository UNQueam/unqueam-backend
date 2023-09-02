package com.unqueam.gamingplatform.core.domain

import org.junit.jupiter.api.Test

class RankBadgeTest {

    @Test
    fun `a rank should move up to the next rank when the neededs views are lower than given views`() {
        assert(RankBadge.RANK_5.shouldMoveUpToTheNextRank(10))
        assert(RankBadge.RANK_4.shouldMoveUpToTheNextRank(25))
        assert(RankBadge.RANK_3.shouldMoveUpToTheNextRank(50))
        assert(RankBadge.RANK_2.shouldMoveUpToTheNextRank(100))
    }

    @Test
    fun `a rank should not move up to the next rank when the neededs views are higher than given views`() {
        assert(!RankBadge.RANK_5.shouldMoveUpToTheNextRank(9))
        assert(!RankBadge.RANK_4.shouldMoveUpToTheNextRank(24))
        assert(!RankBadge.RANK_3.shouldMoveUpToTheNextRank(49))
        assert(!RankBadge.RANK_2.shouldMoveUpToTheNextRank(99))
        assert(!RankBadge.RANK_1.shouldMoveUpToTheNextRank(999))
    }

}