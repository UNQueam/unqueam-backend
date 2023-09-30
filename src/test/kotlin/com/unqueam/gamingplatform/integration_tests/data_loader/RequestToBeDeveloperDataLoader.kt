package com.unqueam.gamingplatform.integration_tests.data_loader

import com.unqueam.gamingplatform.application.dtos.BecomeDeveloperOutput
import com.unqueam.gamingplatform.application.dtos.BecomeDeveloperRequest
import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.core.domain.RequestToBeDeveloper
import com.unqueam.gamingplatform.core.domain.Role
import com.unqueam.gamingplatform.core.services.IDeveloperService
import com.unqueam.gamingplatform.core.services.IUserService
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class RequestToBeDeveloperDataLoader(
    @Autowired private val userService: IUserService,
    @Autowired private val passwordEncoder: PasswordEncoder,
    @Autowired private val developerService: IDeveloperService) {

    @Transactional
    fun loadRequest(username: String = "username", password: String = "Password123", email: String = "some_email@gmail.com", role: Role = Role.USER, id: Long = 1): BecomeDeveloperOutput {
        val platformUser = PlatformUser(id, username, passwordEncoder.encode(password), email, role)
        userService.save(platformUser)

        val request = BecomeDeveloperRequest("Quiero ser desarrollador.")
        return developerService.becomeDeveloper(request, platformUser)
    }
}