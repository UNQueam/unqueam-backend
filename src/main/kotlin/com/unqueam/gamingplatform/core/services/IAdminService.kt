package com.unqueam.gamingplatform.core.services

import com.unqueam.gamingplatform.application.dtos.UserOutput

interface IAdminService {

    fun fetchUsers(): List<UserOutput>
}