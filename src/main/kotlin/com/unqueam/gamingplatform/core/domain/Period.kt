package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.*

@Entity
class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?
    val year: Int
    @Enumerated (EnumType.STRING)
    val semester: Semester

    constructor(anId: Long?, aYear: Int, aSemester: Semester) {
        this.id = anId
        this.year = aYear
        this.semester = aSemester
    }
}