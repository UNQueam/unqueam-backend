package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?
    var rating: Int
    var content: String
    @ManyToOne (fetch = FetchType.LAZY)
    val game: Game
    @ManyToOne (fetch = FetchType.LAZY)
    val publisher: PlatformUser
    var creationTime: LocalDate
    var lastModification: LocalDate

    constructor(
            id:Long?,
            rating: Int,
            content: String,
            game: Game,
            publisher: PlatformUser,
            creationTime: LocalDate,
            lastModification: LocalDate
    ) {
      this.id = id
      this.rating = rating
      this.content = content
      this.game = game
      this.publisher = publisher
      this.creationTime = creationTime
      this.lastModification = lastModification
    }
    fun getPublisherId(): Long = publisher.id!!

    fun update(newContent: String, newRating: Int) {
        this.content = newContent
        this.rating = newRating
    }

}