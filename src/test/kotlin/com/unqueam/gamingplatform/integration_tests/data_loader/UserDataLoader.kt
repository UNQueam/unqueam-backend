package com.unqueam.gamingplatform.integration_tests.data_loader

import com.unqueam.gamingplatform.core.domain.Role
import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.core.services.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
@Profile("test")
class UserDataLoader(@Autowired private val userService: IUserService, @Autowired private val  passwordEncoder: PasswordEncoder) {

    fun loadNewUser(username: String, password: String, email: String = "some_email@gmail.com", role: Role = Role.USER, id: Long = 1): PlatformUser {
        val platformUser: PlatformUser = PlatformUser(id, username, passwordEncoder.encode(password), email, role)
        userService.save(platformUser)
        return platformUser
    }
}