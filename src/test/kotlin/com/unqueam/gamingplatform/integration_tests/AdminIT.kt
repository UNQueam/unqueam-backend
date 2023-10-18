package com.unqueam.gamingplatform.integration_tests

import com.unqueam.gamingplatform.application.http.API
import com.unqueam.gamingplatform.core.domain.Role
import com.unqueam.gamingplatform.infrastructure.configuration.jwt.JwtHelper
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.LinkedMultiValueMap

private const val USERNAME = "Jhon"
private const val PASSWORD = "Doe"

class AdminIT : AbstractIntegrationTest() {

    @Test
    fun `1 - Fetch users as admin, 200 Success OK`() {
        val user = userDataLoader.loadNewUser(USERNAME, PASSWORD, role = Role.ADMIN)
        val token = buildJwtTokenForUser(user)

        val headers = LinkedMultiValueMap<String, String>()
        headers.put(JwtHelper.AUTH_HEADER_KEY, mutableListOf(token))

        getTo(API.ENDPOINT_USERS, headers = headers)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].user_id").value(1))
            .andReturn()
    }
}