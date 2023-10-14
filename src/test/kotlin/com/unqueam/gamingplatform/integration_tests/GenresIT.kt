package com.unqueam.gamingplatform.integration_tests

import com.unqueam.gamingplatform.application.http.API
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class GenresIT : AbstractIntegrationTest() {

    @Test
    fun `test fetch genres`() {
        getTo(API.ENDPOINT_GENRES)
            .andExpect(status().isOk)
            .andReturn()
    }
}