package com.unqueam.gamingplatform.core.domain

enum class RankBadge(val rankName: String, val  nextRank: RankBadge, val neededViews: Long) {
    GOLD("gold", GOLD, 100L) {
        override fun shouldMoveUpToTheNextRank(gameViewsEvents: Long): Boolean {
            return false
        }
    },
    SILVER("silver", GOLD, 50L),
    BRONZE("bronze", SILVER, 15L),
    UNRANKED("unranked", BRONZE, 0L);

    open fun shouldMoveUpToTheNextRank(gameViewsEvents: Long): Boolean {
        return nextRank.neededViews <= gameViewsEvents
    }

}