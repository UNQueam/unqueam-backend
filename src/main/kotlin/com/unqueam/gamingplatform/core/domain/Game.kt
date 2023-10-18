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
    rankBadge: RankBadge,
    genres: Set<Genre>,
    developmentTeam: String,
    publisher: PlatformUser,
    isHidden: Boolean,
    comments: Set<Comment>
) {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    var id: Long? = anId
    @ManyToOne
    val publisher: PlatformUser = publisher
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
    @ElementCollection(targetClass = Genre::class)
    @CollectionTable(name = "game_genres", joinColumns = [JoinColumn(name = "game_id")])
    @Enumerated(EnumType.STRING)
    val genres: Set<Genre> = genres
    val developmentTeam: String = developmentTeam
    var isHidden: Boolean = isHidden
    @OneToMany (cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "game")
    val comments: Set<Comment> = comments

    fun syncWith(updatedGame: Game): Game {
        return Game(
            id,
            updatedGame.name,
            updatedGame.logoUrl,
            updatedGame.description,
            updatedGame.linkToGame,
            updatedGame.releaseDate,
            updatedGame.developers,
            updatedGame.images,
            this.rankBadge,
            updatedGame.genres,
            updatedGame.developmentTeam,
            this.publisher,
            updatedGame.isHidden,
            this.comments
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

    companion object {
        fun builder() : GameBuilder {
            return GameBuilder()
        }
    }

}