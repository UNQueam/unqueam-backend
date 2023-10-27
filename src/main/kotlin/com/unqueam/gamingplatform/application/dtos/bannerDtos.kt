package com.unqueam.gamingplatform.application.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.unqueam.gamingplatform.core.domain.File

data class BannerRequest(
    val title: String,
    @JsonProperty ("rich_text")
    val richText: String,
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
    val fileName: String,
    val type: String,
    @JsonProperty ("byte_array_as_string")
    val byteArrayAsString: String,
    val size: Long
)