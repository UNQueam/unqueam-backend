package com.unqueam.gamingplatform.application.http

import com.unqueam.gamingplatform.application.auth.AuthContextHelper
import com.unqueam.gamingplatform.core.services.IFavoriteGamesService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class FavoriteGamesController {

    private final val favoriteGamesService: IFavoriteGamesService

    constructor(favoriteGamesService: IFavoriteGamesService) {
        this.favoriteGamesService = favoriteGamesService
    }

    @GetMapping ("/api/users/{userId}/games/favorites")
    fun fetchFavoriteGames(@PathVariable userId: Long): ResponseEntity<Any> {
        val favoriteGames = favoriteGamesService.findFavoriteGamesByUser(userId)
        return ResponseEntity.ok(favoriteGames)
    }

    @PostMapping ("/api/games/favorites/{gameId}")
    fun addGameToFavorites(@PathVariable gameId: Long): ResponseEntity<Any> {
        val authenticatedUser = AuthContextHelper.getAuthenticatedUser()
        val addedGame = favoriteGamesService.addGameAsFavorite(authenticatedUser, gameId)
        return ResponseEntity.status(HttpStatus.CREATED).body(addedGame)
    }

    @DeleteMapping ("/api/games/favorites/{gameId}")
    fun removeGameFromFavorites(@PathVariable gameId: Long): ResponseEntity<Any> {
        val authenticatedUser = AuthContextHelper.getAuthenticatedUser()
        val addedGame = favoriteGamesService.deleteGameFromFavorites(authenticatedUser, gameId)
        return ResponseEntity.status(HttpStatus.OK).body(addedGame)
    }
}