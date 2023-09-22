package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.dtos.UserOutput
import com.unqueam.gamingplatform.core.mapper.UserMapper
import com.unqueam.gamingplatform.core.services.IAdminService
import com.unqueam.gamingplatform.infrastructure.persistence.UserRepository

class AdminService :IAdminService {

    private val userRepository: UserRepository
    private val userMapper: UserMapper

    constructor(aUserRepository: UserRepository, aUserMapper: UserMapper ) {
        this.userRepository = aUserRepository
        this.userMapper = aUserMapper
    }

    override fun fetchUsers(): List<UserOutput> {
        return userRepository.findAll().map { userMapper.mapToOutput(it) }
    }
}