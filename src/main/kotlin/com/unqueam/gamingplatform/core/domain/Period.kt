package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.apache.commons.lang3.Validate.notNull

@Entity
class Period {

    @Id
    private val id: Long
    private val year: Int
    @Enumerated (EnumType.STRING)
    private val semester: Semester

    constructor(anId: Long, aYear: Int, aSemester: Semester) {
        this.id = anId
        this.year = notNull(aYear, "A Period must have a year.")
        this.semester = notNull(aSemester, "A Period must have a semester.")
    }
}