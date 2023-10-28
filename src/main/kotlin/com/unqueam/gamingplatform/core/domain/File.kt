package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.*

@Entity
data class File(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    val fileName: String,
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    var byteArray: ByteArray,
    val type: String,
    val size: Long
)