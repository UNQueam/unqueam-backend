package com.unqueam.gamingplatform.core.domain

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
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
    developers: List<Developer>,
    images: List<GameImage>
) {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    val id: Long? = anId
    var name: String = aName
    var logoUrl: String = aLogoUrl
    var description: String = aDescription
    var linkToGame: String = aLinkToGame
    // SMELL: delete JsonDeserialize from this class
    var releaseDate: LocalDate = aReleaseDate
    @ManyToMany (cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val developers: List<Developer> = developers
    @OneToMany (cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val images: List<GameImage> = images

    fun syncWith(updatedGame: Game): Game {
        return Game(
            id,
            updatedGame.name,
            updatedGame.logoUrl,
            updatedGame.description,
            updatedGame.linkToGame,
            updatedGame.releaseDate,
            developers,
            images
        )
    }

}