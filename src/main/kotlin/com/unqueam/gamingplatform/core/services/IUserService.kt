package com.unqueam.gamingplatform.core.services

import com.unqueam.gamingplatform.core.domain.PlatformUser
import java.util.Optional

interface IUserService {

    fun findUserByUsername(username: String): PlatformUser
    fun findUserByUsernameOrEmail(username: String, email: String): Optional<PlatformUser>
    fun save(platformUser: PlatformUser): PlatformUser
    fun findUserById(id: Long): PlatformUser

}