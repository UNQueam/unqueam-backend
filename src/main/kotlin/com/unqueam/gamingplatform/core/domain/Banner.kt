package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.*

@Entity
class Banner {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private val id: Long?
    private val title: String
    private val richText: String
    @ManyToOne (fetch = FetchType.LAZY)
    private val publisher: PlatformUser
    @OneToOne (cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val picture: File

    constructor(id: Long?, title: String, richText: String, publisher: PlatformUser, file: File) {
        this.id = id
        this.title = title
        this.richText = richText
        this.publisher = publisher
        this.picture = file
    }

    fun id(): Long = id!!
    fun title(): String = title
    fun richText(): String = richText
    fun publisherId(): Long = publisher.id!!
    fun publisherUsername(): String = publisher.getUsername()

    fun syncWith(updatedBanner: Banner): Banner {
        return Banner(id, updatedBanner.title, updatedBanner.richText, publisher, updatedBanner.picture)
    }
}