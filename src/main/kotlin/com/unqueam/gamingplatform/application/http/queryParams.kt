package com.unqueam.gamingplatform.application.http

import io.micrometer.common.util.StringUtils

class GetHiddenGamesParam(private val hiddenValue : Boolean?) {

    /**
     * It returns if should get all games (including hidden games).
     * Example:
     * Admin can fetch all games.
     * PlatformUser (User) should only fetch no hidden games.
     */
    fun shouldFetchAllGames(): Boolean {
        if (hiddenValue == null) { return false }
        return hiddenValue
    }
}

data class GetBannersParams(val alias: String?) {
    fun shouldFilterByAlias(): Boolean {
        return StringUtils.isNotBlank(alias)
    }
}