package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.core.domain.User
import com.unqueam.gamingplatform.core.services.IUserService
import com.unqueam.gamingplatform.infrastructure.persistence.UserRepository

class UserService(private val userRepository: UserRepository) : IUserService {
    override fun findUserByUsername(username: String): User {
        return userRepository.findByUsername(username)
    }
}