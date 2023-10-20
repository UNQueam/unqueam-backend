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
    val name: String

    constructor(anId: Long?, name: String) {
        this.id = anId
        this.name = notBlank(name, "A Developer must have a name.")
    }
}