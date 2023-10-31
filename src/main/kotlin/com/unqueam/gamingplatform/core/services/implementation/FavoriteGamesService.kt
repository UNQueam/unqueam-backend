package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.dtos.FavoriteGameOutput
import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.core.exceptions.Exceptions
import com.unqueam.gamingplatform.core.mapper.FavoritesGamesMapper
import com.unqueam.gamingplatform.core.services.IFavoriteGamesService
import com.unqueam.gamingplatform.core.services.IUserService
import com.unqueam.gamingplatform.infrastructure.persistence.FavoriteGamesRepository
import com.unqueam.gamingplatform.infrastructure.persistence.GameRepository
import jakarta.persistence.EntityNotFoundException

class FavoriteGamesService : IFavoriteGamesService {

    private val userService: IUserService
    private val favoriteGamesRepository: FavoriteGamesRepository
    private val gamesRepository: GameRepository
    private val favoritesGamesMapper: FavoritesGamesMapper

    constructor(userService: IUserService, favoriteGamesRepository: FavoriteGamesRepository, gamesRepository: GameRepository, favoritesGamesMapper: FavoritesGamesMapper) {
        this.userService = userService
        this.favoriteGamesRepository = favoriteGamesRepository
        this.gamesRepository = gamesRepository
        this.favoritesGamesMapper = favoritesGamesMapper
    }

    override fun findFavoriteGamesByUser(userId: Long): Collection<FavoriteGameOutput> {
        return favoriteGamesRepository
            .findAllByPlatformUser(userId)
            .map { favoritesGamesMapper.mapToOutput(it) }
    }

    override fun addGameAsFavorite(authenticatedUser: PlatformUser, gameId: Long): FavoriteGameOutput {
        val game = gamesRepository.findById(gameId).orElseThrow { EntityNotFoundException(Exceptions.GAME_NOT_FOUND_ERROR_MESSAGE.format(gameId)) }

        val addedGame = authenticatedUser.addAsFavoriteGame(game)

        userService.save(authenticatedUser)
        return favoritesGamesMapper.mapToOutput(addedGame)
    }

    override fun deleteGameFromFavorites(authenticatedUser: PlatformUser, gameId: Long): FavoriteGameOutput {
        val game = favoriteGamesRepository.findByPlatformUserIdAndGameId(authenticatedUser.id!!, gameId).orElseThrow { EntityNotFoundException(Exceptions.FAVORITE_GAME_NOT_FOUND) }
        favoriteGamesRepository.deleteById(game.id!!)
        return favoritesGamesMapper.mapToOutput(game)
    }

}