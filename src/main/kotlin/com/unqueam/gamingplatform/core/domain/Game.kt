package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import java.time.LocalDate

@Entity
class Game(
    anId: Long,
    aName: String,
    aLogoUrl: String,
    aDescription: String,
    aLinkToGame: String,
    aReleaseDate: LocalDate,
    developers: List<Developer>,
    images: List<GameImage>
) {

    @Id
    private val id: Long = anId
    private val name: String = aName
    private val logoUrl: String = aLogoUrl
    private val description: String = aDescription
    private val linkToGame: String = aLinkToGame
    private val releaseDate: LocalDate = aReleaseDate
    @ManyToMany
    private val developers: List<Developer> = developers
    @OneToMany
    private val images: List<GameImage> = images

}