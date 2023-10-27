package com.unqueam.gamingplatform.application.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.unqueam.gamingplatform.core.domain.File
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class BannerRequest(
    @field:NotBlank (message = "El nombre del producto no puede estar en blanco")
    val title: String,
    @JsonProperty ("rich_text")
    val richText: String,
    @field:NotNull (message = "El banner debe contener una ilustración")
    val picture: FileInput
)

data class BannerOutput(
    @JsonProperty ("banner_id")
    val bannerId: Long,
    val title: String,
    @JsonProperty ("rich_text")
    val richText: String,
    val publisher: PublisherOutput,
    val picture: File
)

data class FileInput(
    @JsonProperty ("file_name")
    @field:NotBlank (message = "El archivo debe tener un nombre")
    val fileName: String,
    @field:NotBlank (message = "El archivo debe tener un tipo de archivo (img, mp3, ...)")
    val type: String,
    @JsonProperty ("byte_array_as_string")
    @field:NotBlank (message = "El archivo debe tener el ByteArray que lo conforma")
    val byteArrayAsString: String,
    @field:NotNull (message = "El archivo debe contener el tamaño que ocupa")
    val size: Long
)