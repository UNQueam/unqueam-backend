package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.core.domain.User
import com.unqueam.gamingplatform.core.services.IUserService
import com.unqueam.gamingplatform.infrastructure.persistence.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException

class UserService(private val userRepository: UserRepository) : IUserService {

    override fun findUserByUsername(username: String): User {
        return userRepository
            .findByUsername(username)
            .orElseThrow { UsernameNotFoundException("Usuario no encontrado: $username") }
    }

    override fun save(user: User): User {
        return userRepository.save(user)
    }
}