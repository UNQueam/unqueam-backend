package com.unqueam.gamingplatform.core.domain

import org.junit.jupiter.api.Test

class RankBadgeTest {

    @Test
    fun `a rank should move up to the next rank when the neededs views are lower than given views`() {
        assert(RankBadge.UNRANKED.shouldMoveUpToTheNextRank(15))
        assert(RankBadge.BRONZE.shouldMoveUpToTheNextRank(50))
        assert(RankBadge.SILVER.shouldMoveUpToTheNextRank(100))
    }

    @Test
    fun `a rank should not move up to the next rank when the neededs views are higher than given views`() {
        assert(!RankBadge.UNRANKED.shouldMoveUpToTheNextRank(14))
        assert(!RankBadge.BRONZE.shouldMoveUpToTheNextRank(49))
        assert(!RankBadge.SILVER.shouldMoveUpToTheNextRank(99))
        assert(!RankBadge.GOLD.shouldMoveUpToTheNextRank(999))
    }

}