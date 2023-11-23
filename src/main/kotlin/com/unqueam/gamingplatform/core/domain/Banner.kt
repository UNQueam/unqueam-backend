package com.unqueam.gamingplatform.core.domain

import com.unqueam.gamingplatform.core.helper.KebabConverter.toKebabCase
import jakarta.persistence.*

@Entity
class Banner {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private val id: Long?
    private val title: String
    private val alias: String
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private val richText: String
    @ManyToOne (fetch = FetchType.LAZY)
    private val publisher: PlatformUser
    @OneToOne (cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val picture: File
    var isActive: Boolean

    constructor(id: Long?, title: String, alias: String, richText: String, publisher: PlatformUser, file: File, isActive: Boolean) {
        this.id = id
        this.title = title
        this.alias = alias
        this.richText = richText
        this.publisher = publisher
        this.picture = file
        this.isActive = isActive
    }

    fun id(): Long = id!!
    fun title(): String = title
    fun richText(): String = richText
    fun alias(): String = alias
    fun publisherId(): Long = publisher.id!!
    fun publisherUsername(): String = publisher.getUsername()

    fun hasAlias(someAlias: String): Boolean = toKebabCase(alias) == toKebabCase(someAlias)

    fun syncWith(updatedBanner: Banner): Banner {
        return Banner(
            id,
            updatedBanner.title,
            updatedBanner.alias,
            updatedBanner.richText,
            publisher,
            updatedBanner.picture,
            this.isActive)
    }

    fun activate() {
        this.isActive = true
    }

    fun deactivate() {
        this.isActive = false
    }

    fun publisherProfileImage(): String? {
        return publisher.getProfile().imageId
    }
}