package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.apache.commons.lang3.Validate.notBlank

@Entity
class Developer {

    @Id
    private val id: Long
    private val firstName: String
    private val lastName: String

    constructor(anId: Long, aFirstName: String, aLastName: String) {
        this.id = anId
        this.firstName = notBlank(aFirstName, "A Developer must have a first name.")
        this.lastName = notBlank(aLastName, "A Developer must have a last name.")
    }
}