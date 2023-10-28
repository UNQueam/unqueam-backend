package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.dtos.BannerOutput
import com.unqueam.gamingplatform.application.dtos.BannerRequest
import com.unqueam.gamingplatform.core.domain.Banner
import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.core.domain.Role
import com.unqueam.gamingplatform.core.exceptions.Exceptions
import com.unqueam.gamingplatform.core.exceptions.Exceptions.NOT_EXISTS_BANNER_WITH_ID
import com.unqueam.gamingplatform.core.exceptions.Exceptions.YOU_ARE_NOT_AUTHORIZED_TO_DO_THIS_ACTION_DUE_TO_YOUR_ROLE
import com.unqueam.gamingplatform.core.exceptions.InvalidBannerDataException
import com.unqueam.gamingplatform.core.exceptions.UserNotAuthorizedByRoleException
import com.unqueam.gamingplatform.core.helper.KebabConverter
import com.unqueam.gamingplatform.core.mapper.BannerMapper
import com.unqueam.gamingplatform.core.services.IBannerService
import com.unqueam.gamingplatform.infrastructure.persistence.BannerRepository
import jakarta.persistence.EntityNotFoundException
import java.util.*

class BannerService : IBannerService {

    private val bannerRepository: BannerRepository
    private val bannerMapper: BannerMapper

    constructor(bannerRepository: BannerRepository, bannerMapper: BannerMapper) {
        this.bannerRepository = bannerRepository
        this.bannerMapper = bannerMapper
    }

    override fun findBannerById(bannerId: Long): BannerOutput {
        val banner = searchById(bannerId)
        return bannerMapper.mapToOutput(banner)
    }

    override fun findBanners(): List<BannerOutput> {
        return bannerRepository.findAll().map { bannerMapper.mapToOutput(it) }
    }

    override fun postBanner(bannerRequest: BannerRequest, publisher: PlatformUser): BannerOutput {
        validateRequest(bannerRequest, publisher, Optional.empty())
        val banner = bannerMapper.mapToInput(bannerRequest, publisher)
        bannerRepository.save(banner)
        return bannerMapper.mapToOutput(banner)
    }

    private fun validateRequest(bannerRequest: BannerRequest, publisher: PlatformUser, bannerToUpdate: Optional<Banner>) {
        validateUserHasRoleAdmin(publisher)

        val errors: MutableMap<String, List<String>> = mutableMapOf()
        val existsByAlias = bannerRepository.existsByAlias(KebabConverter.toKebabCase(bannerRequest.alias))

        val existsByAliasAndIsPublishingNewBanner = existsByAlias && bannerToUpdate.isEmpty
        val existsByAliasAndIsEditingAndAliasWasModified = existsByAlias && bannerToUpdate.isPresent && !bannerToUpdate.get().hasAlias(bannerRequest.alias)

        if (existsByAliasAndIsPublishingNewBanner || existsByAliasAndIsEditingAndAliasWasModified)
            errors["alias"] = listOf(Exceptions.THE_ALIAS_IS_ALREADY_IN_USE)

        if (errors.isNotEmpty()) throw InvalidBannerDataException(errors)
    }

    override fun deleteBannerById(bannerId: Long): BannerOutput {
        val bannerToRemove = findBannerById(bannerId)
        bannerRepository.deleteById(bannerId)
        return bannerToRemove
    }

    override fun updateBannerById(bannerId: Long, bannerRequest: BannerRequest, publisher: PlatformUser): BannerOutput {
        val bannerToUpdate = searchById(bannerId)

        validateRequest(bannerRequest, publisher, Optional.of(bannerToUpdate))

        val bannerFromRequest = bannerMapper.mapToInput(bannerRequest, publisher)
        val updatedBanner = bannerToUpdate.syncWith(bannerFromRequest)
        return bannerMapper.mapToOutput(bannerRepository.save(updatedBanner))
    }

    private fun validateUserHasRoleAdmin(user: PlatformUser) {
        if (!user.hasRole(Role.ADMIN))
            throw UserNotAuthorizedByRoleException(YOU_ARE_NOT_AUTHORIZED_TO_DO_THIS_ACTION_DUE_TO_YOUR_ROLE)
    }

    private fun searchById(bannerId: Long): Banner {
        return bannerRepository
            .findById(bannerId)
            .orElseThrow { EntityNotFoundException(NOT_EXISTS_BANNER_WITH_ID.format(bannerId)) }
    }
}