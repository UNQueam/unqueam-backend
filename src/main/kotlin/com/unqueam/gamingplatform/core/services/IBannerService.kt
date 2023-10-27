package com.unqueam.gamingplatform.core.services

import com.unqueam.gamingplatform.application.dtos.BannerOutput
import com.unqueam.gamingplatform.application.dtos.BannerRequest
import com.unqueam.gamingplatform.core.domain.PlatformUser

interface IBannerService {
    fun findBannerById(bannerId: Long): BannerOutput
    fun findBanners(): List<BannerOutput>
    fun postBanner(bannerRequest: BannerRequest, publisher: PlatformUser): BannerOutput
    fun deleteBannerById(bannerId: Long): BannerOutput
    fun updateBannerById(bannerId: Long, bannerRequest: BannerRequest, publisher: PlatformUser): BannerOutput
}
