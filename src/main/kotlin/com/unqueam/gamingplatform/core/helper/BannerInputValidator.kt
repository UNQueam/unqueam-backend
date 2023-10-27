package com.unqueam.gamingplatform.core.helper

import com.unqueam.gamingplatform.application.dtos.BannerRequest
import com.unqueam.gamingplatform.core.exceptions.Exceptions.YOU_MUST_INSERT_A_CONTENT
import com.unqueam.gamingplatform.core.exceptions.Exceptions.YOU_MUST_INSERT_A_PICTURE_FOR_THE_BANNER
import com.unqueam.gamingplatform.core.exceptions.Exceptions.YOU_MUST_INSERT_A_TITLE_FOR_THE_BANNER
import com.unqueam.gamingplatform.core.exceptions.InvalidBannerDataException
import org.apache.commons.lang3.StringUtils.isBlank
import java.util.LinkedHashMap

class BannerInputValidator(private val bannerRequest: BannerRequest) {

    fun validate() {
        val errors = LinkedHashMap<String, List<String>>()

        if (isBlank(bannerRequest.title)) errors["title"] = listOf(YOU_MUST_INSERT_A_TITLE_FOR_THE_BANNER)
        if (isBlank(bannerRequest.richText)) errors["rich_text"] = listOf(YOU_MUST_INSERT_A_CONTENT)
        if (isBlank(bannerRequest.picture.byteArrayAsString)) errors["byte_array_as_string"] = listOf(YOU_MUST_INSERT_A_PICTURE_FOR_THE_BANNER)

        if (errors.isNotEmpty()) throw InvalidBannerDataException(errors)
    }
}