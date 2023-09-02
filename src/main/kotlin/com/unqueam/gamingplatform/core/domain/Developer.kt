package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.apache.commons.lang3.Validate.notBlank

@Entity
class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?
    val firstName: String
    val lastName: String

    constructor(anId: Long?, aFirstName: String, aLastName: String) {
        this.id = anId
        this.firstName = notBlank(aFirstName, "A Developer must have a first name.")
        this.lastName = notBlank(aLastName, "A Developer must have a last name.")
    }
}