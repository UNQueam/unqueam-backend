package com.unqueam.gamingplatform.core.domain

import java.time.LocalDate

class GameBuilder {

    private lateinit var name: String
    private lateinit var description: String
    private lateinit var linkToGame: String
    private var developers: Set<Developer> = setOf()
    private lateinit var releaseDate: LocalDate
    private lateinit var logoUrl: String
    private var images: Set<GameImage> = setOf()
    private var genres: Set<Genre> = setOf()
    private lateinit var developmentTeam: String
    private lateinit var publisher: PlatformUser
    private var isHidden: Boolean = false
    private var comments: Set<Comment> = setOf()
    private lateinit var alias: String
    private var linkToDownload: String? = null
    private var period: Period? = null

    fun withPeriod(period: Period?): GameBuilder {
        this.period = period
        return this
    }

    fun developedBy(developers: Set<Developer>) : GameBuilder {
        this.developers = developers
        return this
    }

    fun publishedBy(publisher: PlatformUser) : GameBuilder {
        this.publisher = publisher
        return this
    }

    fun isHidden(isHiddenValue: Boolean) : GameBuilder {
        this.isHidden = isHiddenValue
        return this
    }

    fun withDevelopmentTeam(developmentTeam: String) : GameBuilder {
        this.developmentTeam = developmentTeam
        return this
    }

    fun withGenres(genres: Set<Genre>) : GameBuilder {
        this.genres = genres
        return this
    }

    fun withComments(comments: Set<Comment>) : GameBuilder {
        this.comments = comments
        return this
    }

    fun releasedAt(date: LocalDate) : GameBuilder {
        this.releaseDate = date
        return this
    }

    fun describedAs(description: String) : GameBuilder {
        this.description = description
        return this
    }

    fun named(name: String) : GameBuilder {
        this.name = name
        return this
    }

    fun withLinkToGame(linkToGame: String) : GameBuilder {
        this.linkToGame = linkToGame
        return this
    }

    fun withLogoUrl(logoUrl: String) : GameBuilder {
        this.logoUrl = logoUrl
        return this
    }

    fun withImages(images: Set<GameImage>) : GameBuilder {
        this.images = images
        return this
    }

    fun withAlias(alias: String) : GameBuilder {
        this.alias = alias
        return this
    }

    fun withLinkToDownload(linkToDownload: String?) : GameBuilder {
        this.linkToDownload = linkToDownload
        return this
    }

    fun build() : Game {
        return Game(
            null,
            name,
            logoUrl,
            description,
            linkToGame,
            releaseDate,
            developers,
            images,
            RankBadge.UNRANKED,
            genres,
            developmentTeam,
            publisher,
            isHidden,
            comments,
            alias,
            linkToDownload,
            period
        )
    }
}