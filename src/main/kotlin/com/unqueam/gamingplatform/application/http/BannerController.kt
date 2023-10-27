package com.unqueam.gamingplatform.application.http

import com.unqueam.gamingplatform.application.auth.AuthContextHelper
import com.unqueam.gamingplatform.application.dtos.BannerRequest
import com.unqueam.gamingplatform.core.services.IBannerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(API.ENDPOINT_BANNERS)
class BannerController {

    private val bannerService: IBannerService

    @Autowired
    constructor(bannerService: IBannerService) {
        this.bannerService = bannerService
    }

    @GetMapping ("/{bannerId}")
    fun findBannerById(@PathVariable bannerId: Long) : ResponseEntity<Any> {
        val output = bannerService.findBannerById(bannerId)
        return ResponseEntity.ok(output)
    }

    @GetMapping
    fun findBanners() : ResponseEntity<Any> {
        val output = bannerService.findBanners()
        return ResponseEntity.ok(output)
    }

    @PostMapping
    fun postBanner(@Valid @RequestBody bannerRequest: BannerRequest) : ResponseEntity<Any> {
        val authenticatedUser = AuthContextHelper.getAuthenticatedUser()
        val output = bannerService.postBanner(bannerRequest, authenticatedUser)
        return ResponseEntity.status(HttpStatus.CREATED).body(output)
    }

    @DeleteMapping ("/{bannerId}")
    fun deleteBannerById(@PathVariable bannerId: Long) : ResponseEntity<Any> {
        val output = bannerService.deleteBannerById(bannerId)
        return ResponseEntity.status(HttpStatus.OK).body(output)
    }

    @PutMapping ("/{bannerId}")
    fun updateBannerById(@PathVariable bannerId: Long, @Valid @RequestBody bannerRequest: BannerRequest) : ResponseEntity<Any> {
        val authenticatedUser = AuthContextHelper.getAuthenticatedUser()
        val output = bannerService.updateBannerById(bannerId, bannerRequest, authenticatedUser)
        return ResponseEntity.status(HttpStatus.OK).body(output)
    }
}