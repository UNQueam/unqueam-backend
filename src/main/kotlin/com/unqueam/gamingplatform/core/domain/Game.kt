package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Game(
    anId: Long?,
    aName: String,
    aLogoUrl: String,
    aDescription: String,
    aLinkToGame: String,
    aReleaseDate: LocalDate,
    developers: Set<Developer>,
    images: Set<GameImage>,
    rankBadge: RankBadge
) {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    val id: Long? = anId
    var name: String = aName
    var logoUrl: String = aLogoUrl
    var description: String = aDescription
    var linkToGame: String = aLinkToGame
    var releaseDate: LocalDate = aReleaseDate
    @ManyToMany (cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val developers: Set<Developer> = developers
    @OneToMany (cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val images: Set<GameImage> = images
    @Enumerated (EnumType.STRING)
    var rankBadge: RankBadge = rankBadge

    fun syncWith(updatedGame: Game): Game {
        return Game(
            id,
            updatedGame.name,
            updatedGame.logoUrl,
            updatedGame.description,
            updatedGame.linkToGame,
            updatedGame.releaseDate,
            developers,
            images,
            this.rankBadge
        )
    }

    /**
     * This method move up the game rank to the next rank if rank meets the requirements.
     * @return Boolean true if the game has changed his rank.
     */
    fun checkAndUpdateRankIfMeetsTheRequirements(gameViewsEvents: Long): Boolean {
        if (this.rankBadge.shouldMoveUpToTheNextRank(gameViewsEvents)) {
            this.rankBadge = rankBadge.nextRank
            return true
        }
        return false
    }

}