package com.unqueam.gamingplatform.core.domain

import java.util.*

enum class FileType(private val fileTypeName: String) {

    IMAGE("image"),
    UNKNOWN("unknown");

    companion object {
        fun getFileTypeBy(fileTypeName: String): FileType {
            return Optional.ofNullable(values().find { it.fileTypeName == fileTypeName }).orElse(UNKNOWN)
        }
    }
}