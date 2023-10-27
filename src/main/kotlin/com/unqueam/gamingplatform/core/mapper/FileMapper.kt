package com.unqueam.gamingplatform.core.mapper

import com.unqueam.gamingplatform.application.dtos.FileInput
import com.unqueam.gamingplatform.core.domain.File
import com.unqueam.gamingplatform.core.domain.FileType

class FileMapper {
    fun mapToInput(fileInput: FileInput): File {
        return File(null, fileInput.fileName, fileInput.byteArrayAsString.toByteArray(), FileType.getFileTypeBy(fileInput.type), fileInput.size)
    }

    fun mapToOutput(file: File): File {
        return file
    }
}
