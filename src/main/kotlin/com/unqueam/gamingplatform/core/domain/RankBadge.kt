package com.unqueam.gamingplatform.core.domain

enum class RankBadge(val rankName: String, val  nextRank: RankBadge, val neededViews: Long) {
    RANK_1("rank1", RANK_1, 100L),
    RANK_2("rank2", RANK_1, 50L),
    RANK_3("rank3", RANK_2, 25L),
    RANK_4("rank4", RANK_3, 10L),
    RANK_5("rank5", RANK_4, 0L);

    fun shouldPassToNextRank(gameViewsEvents: Long): Boolean {
        return this.neededViews <= gameViewsEvents
    }

}