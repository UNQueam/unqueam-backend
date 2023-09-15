package com.unqueam.gamingplatform.core.services

import com.unqueam.gamingplatform.core.domain.User

interface IUserService {

    fun findUserByUsername(username: String): User?
}