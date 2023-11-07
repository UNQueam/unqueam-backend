package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.*

@Entity
class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "period_id")
    var id: Long? = null
    @Column (name = "period_year")
    val year: Int
    @Enumerated (EnumType.STRING)
    val semester: Semester

    constructor(anId: Long?, aYear: Int, aSemester: Semester) {
        this.id = anId
        this.year = aYear
        this.semester = aSemester
    }

    fun syncWith(updatedPeriod: Period?): Period? {
        if (updatedPeriod == null) return null
        return Period(
            id,
            updatedPeriod.year,
            updatedPeriod.semester
        )
    }
}