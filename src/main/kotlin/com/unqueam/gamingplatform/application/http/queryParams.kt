package com.unqueam.gamingplatform.application.http

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