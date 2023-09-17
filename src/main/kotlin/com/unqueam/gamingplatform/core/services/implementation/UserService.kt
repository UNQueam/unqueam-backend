package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.dtos.UserOutput
import com.unqueam.gamingplatform.core.domain.User
import com.unqueam.gamingplatform.core.mapper.UserMapper
import com.unqueam.gamingplatform.core.services.IUserService
import com.unqueam.gamingplatform.infrastructure.persistence.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.util.Optional

class UserService(private val userRepository: UserRepository, private val userMapper: UserMapper ) : IUserService {

    override fun findUserByUsername(username: String): User {
        return userRepository
            .findByUsername(username)
            .orElseThrow { UsernameNotFoundException("Usuario no encontrado: $username") }
    }

    override fun findUserByUsernameOrEmail(username: String, email: String): Optional<User> {
        return userRepository.findByUsernameOrEmail(username, email)
    }

    override fun save(user: User): User {
        return userRepository.save(user)
    }

    override fun findAllUsers(): List<UserOutput> {
        return userRepository.findAll().map { userMapper.mapToOutput(it) }
    }
}