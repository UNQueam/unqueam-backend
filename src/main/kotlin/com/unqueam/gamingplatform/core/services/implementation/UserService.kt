package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.core.services.IUserService
import com.unqueam.gamingplatform.infrastructure.persistence.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.util.Optional

class UserService(private val userRepository: UserRepository) : IUserService {

    override fun findUserByUsername(username: String): PlatformUser {
        return userRepository
            .findByUsername(username)
            .orElseThrow { UsernameNotFoundException("Usuario no encontrado: $username") }
    }

    override fun findUserById(id: Long): PlatformUser {
        return userRepository
            .findById(id)
            .orElseThrow { UsernameNotFoundException("Usuario no encontrado: $id") }
    }

    override fun findUserByUsernameOrEmail(username: String, email: String): Optional<PlatformUser> {
        return userRepository.findByUsernameOrEmail(username, email)
    }

    override fun save(platformUser: PlatformUser): PlatformUser {
        return userRepository.save(platformUser)
    }
}