package com.unqueam.gamingplatform.core.mapper

import com.unqueam.gamingplatform.application.dtos.FileInput
import com.unqueam.gamingplatform.application.dtos.FileOutput
import com.unqueam.gamingplatform.core.domain.File
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

class FileMapper {
    fun mapToInput(fileInput: FileInput): File {
        return File(
            null,
            fileInput.fileName,
            compressData(fileInput.byteArrayAsString.toByteArray()),
            fileInput.type,
            fileInput.size)
    }

    fun mapToOutput(file: File): FileOutput {
        return FileOutput(
            id = file.id!!,
            fileName = file.fileName,
            type = file.type,
            byteArrayAsString = decompressData(file.byteArray).decodeToString(),
            size = file.size
        )
    }

    fun compressData(data: ByteArray): ByteArray {
        val byteStream = ByteArrayOutputStream()
        val gzipStream = GZIPOutputStream(byteStream)
        gzipStream.write(data)
        gzipStream.close()
        return byteStream.toByteArray()
    }

    fun decompressData(compressedData: ByteArray): ByteArray {
        val byteStream = ByteArrayInputStream(compressedData)
        val gzipStream = GZIPInputStream(byteStream)
        return gzipStream.readBytes()
    }
}
