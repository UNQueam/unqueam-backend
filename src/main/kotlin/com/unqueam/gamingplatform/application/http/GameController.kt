package com.unqueam.gamingplatform.application.http

import com.unqueam.gamingplatform.application.auth.AuthContextHelper
import com.unqueam.gamingplatform.application.dtos.GameOutput
import com.unqueam.gamingplatform.application.dtos.GameRequest
import com.unqueam.gamingplatform.core.services.IGameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping (API.ENDPOINT_GAMES)
class GameController {

    private final val gameService: IGameService

    @Autowired
    constructor(aGameService: IGameService) {
        this.gameService = aGameService
    }

    @PostMapping
    fun publishGame(@RequestBody gameRequest: GameRequest) : ResponseEntity<Any> {
        val publisher = AuthContextHelper.getAuthenticatedUser()
        gameService.publishGame(gameRequest, publisher)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping
    fun fetchGames(@RequestParam (required = false) username: String?): ResponseEntity<List<GameOutput>> {
        val usernameParam = Optional.ofNullable(username)
        val games: List<GameOutput> = gameService.fetchGames(usernameParam)
        return ResponseEntity.status(HttpStatus.OK).body(games)
    }

    @GetMapping (API.ID_PATH_VARIABLE)
    fun fetchGameById(@PathVariable id: Long) : ResponseEntity<GameOutput> {
        val game: GameOutput = gameService.fetchGameById(id)
        return ResponseEntity.status(HttpStatus.OK).body(game)
    }

    @DeleteMapping (API.ID_PATH_VARIABLE)
    fun deleteGameById(@PathVariable id: Long) : ResponseEntity<Any> {
        gameService.deleteGameById(id)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @PutMapping (API.ID_PATH_VARIABLE)
    fun updateGameById(@PathVariable id: Long, @RequestBody updatedGame: GameRequest) : ResponseEntity<Any> {
        val authenticatedUser = AuthContextHelper.getAuthenticatedUser()
        gameService.updateGameById(id, updatedGame, authenticatedUser)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

}
