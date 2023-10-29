package com.unqueam.gamingplatform.application.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class BannerRequest(
    val title: String,
    val alias: String,
    @JsonProperty ("rich_text")
    val richText: String,
    val picture: FileInput
)

data class BannerOutput(
    @JsonProperty ("banner_id")
    val bannerId: Long,
    val title: String,
    val alias: String,
    @JsonProperty ("rich_text")
    val richText: String,
    val publisher: PublisherOutput,
    val picture: FileOutput,
    @JsonProperty ("is_active")
    val isActive: Boolean
)

data class FileInput(
    @JsonProperty ("file_name")
    val fileName: String,
    val type: String,
    @JsonProperty ("byte_array_as_string")
    val byteArrayAsString: String,
    val size: Long
)

data class FileOutput(
    val id: Long,
    @JsonProperty ("file_name")
    val fileName: String,
    val type: String,
    @JsonProperty ("byte_array_as_string")
    val byteArrayAsString: String,
    val size: Long
)
