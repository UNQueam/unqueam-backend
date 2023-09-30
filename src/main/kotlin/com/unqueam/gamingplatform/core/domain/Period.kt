package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.*

@Entity
class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "period_id")
    val id: Long?
    @Column (name = "period_year")
    val year: Int
    @Enumerated (EnumType.STRING)
    val semester: Semester

    constructor(anId: Long?, aYear: Int, aSemester: Semester) {
        this.id = anId
        this.year = aYear
        this.semester = aSemester
    }
}