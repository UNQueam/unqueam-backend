package com.unqueam.gamingplatform.integration_tests

import com.fasterxml.jackson.core.type.TypeReference
import com.unqueam.gamingplatform.application.http.API
import com.unqueam.gamingplatform.core.domain.Genre
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class GenresIT : AbstractIntegrationTest() {

    @Test
    fun `test fetch genres`() {
        getTo(API.ENDPOINT_GENRES)
            .andExpect(status().isOk)
    }
}