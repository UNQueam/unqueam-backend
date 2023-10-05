package com.unqueam.gamingplatform.integration_tests

import com.unqueam.gamingplatform.application.http.API
import com.unqueam.gamingplatform.core.domain.Role
import com.unqueam.gamingplatform.integration_tests.data_loader.UserDataLoader
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

private const val USERNAME = "Jhon"
private const val PASSWORD = "Doe"

class AdminIT : AbstractIntegrationTest() {

    private lateinit var userDataLoader: UserDataLoader

    @BeforeEach
    fun setup(@Autowired userDataLoader: UserDataLoader) {
        this.userDataLoader = userDataLoader
    }

    @Test
    fun `1 - Fetch users as admin, 200 Success OK`() {
        userDataLoader.loadNewUser(USERNAME, PASSWORD, role = Role.USER)

        getTo(API.ENDPOINT_USERS)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].user_id").value(1))
    }
}