package com.unqueam.gamingplatform.core.services

import com.unqueam.gamingplatform.core.domain.User
import java.util.Optional

interface IUserService {

    fun findUserByUsername(username: String): User
    fun findUserByUsernameOrEmail(username: String, email: String): Optional<User>
    fun save(user: User): User
}