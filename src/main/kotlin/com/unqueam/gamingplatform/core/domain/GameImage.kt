package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.apache.commons.lang3.Validate

@Entity
class GameImage {

    @Id
    private val id: Long
    private val url: String

    constructor(id: Long, url: String) {
        this.id = id
        this.url = Validate.notBlank(url, "An Image must have a url.")
    }
}