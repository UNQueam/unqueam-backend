package com.unqueam.gamingplatform.core.domain

import jakarta.persistence.*

@Entity
data class File(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    val fileName: String,
    @Lob
    var file: ByteArray,
    val type: FileType,
    val size: Long
)