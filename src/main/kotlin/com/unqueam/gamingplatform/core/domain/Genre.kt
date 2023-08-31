package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.apache.commons.lang3.Validate

@Entity
class Genre {

    @Id
    private val id: Long
    private val name: String

    constructor(id: Long, name: String) {
        this.id = id
        this.name = Validate.notBlank(name, "A Genre must have a name.")
    }
}