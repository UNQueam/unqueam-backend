package com.unqueam.gamingplatform.core.mapper

import com.unqueam.gamingplatform.application.dtos.*
import com.unqueam.gamingplatform.core.domain.Banner
import com.unqueam.gamingplatform.core.domain.File
import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.core.helper.BannerInputValidator

class BannerMapper(val fileMapper: FileMapper) {

    fun mapToInput(bannerRequest: BannerRequest, publisher: PlatformUser): Banner {
        BannerInputValidator(bannerRequest).validate()
        return Banner(null, bannerRequest.title, bannerRequest.alias, bannerRequest.richText, publisher, mapToFile(bannerRequest.picture), false)
    }

    fun mapToOutput(banner: Banner): BannerOutput {
        return BannerOutput(
            bannerId = banner.id(),
            title = banner.title(),
            alias = banner.alias(),
            richText = banner.richText(),
            publisher = buildPublisherOutput(banner),
            picture = fileMapper.mapToOutput(banner.picture),
            isActive = banner.isActive
        )
    }

    private fun buildPublisherOutput(banner: Banner): PublisherOutput {
        return PublisherOutput(banner.publisherId(), banner.publisherUsername())
    }

    private fun mapToFile(fileInput: FileInput): File {
        return fileMapper.mapToInput(fileInput)
    }
}
