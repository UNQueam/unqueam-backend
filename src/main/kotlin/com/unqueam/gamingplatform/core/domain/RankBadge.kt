package com.unqueam.gamingplatform.core.domain

enum class RankBadge(val rankName: String, val  nextRank: RankBadge, val neededViews: Long) {
    GOLD("rank1", GOLD, 100L) {
        override fun shouldMoveUpToTheNextRank(gameViewsEvents: Long): Boolean {
            return false
        }
    },
    SILVER("rank2", GOLD, 50L),
    BRONZE("rank3", SILVER, 15L),
    UNRANKED("unranked", BRONZE, 0L);

    open fun shouldMoveUpToTheNextRank(gameViewsEvents: Long): Boolean {
        return nextRank.neededViews <= gameViewsEvents
    }

}