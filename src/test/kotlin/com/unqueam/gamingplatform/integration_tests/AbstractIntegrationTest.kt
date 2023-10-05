package com.unqueam.gamingplatform.integration_tests

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.unqueam.gamingplatform.UnqueamApplication
import com.unqueam.gamingplatform.infrastructure.configuration.jwt.JwtAuthenticationFilter
import org.apache.commons.lang3.StringUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.context.WebApplicationContext

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [UnqueamApplication::class, JwtAuthenticationFilter::class])
@WebAppConfiguration
@TestPropertySource(locations = ["classpath:application-test.properties"])
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Profile("test")
abstract class AbstractIntegrationTest {

    @Autowired
    private val webApplicationContext: WebApplicationContext? = null

    protected lateinit var mockMvc: MockMvc
    protected lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext!!).build()
        objectMapper = ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false)
    }

    fun asJson(anObject: Any): String {
        return objectMapper.writeValueAsString(anObject)
    }

    fun postTo(endpoint: String, bodyContent: String): ResultActions {
        return mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post(endpoint)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyContent)
            )
    }

    fun putTo(endpoint: String, bodyContent: String = StringUtils.EMPTY): ResultActions {
        return mockMvc
            .perform(
                MockMvcRequestBuilders
                    .put(endpoint)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyContent)
            )
    }

    fun getTo(endpoint: String, headers: MultiValueMap<String, String> = LinkedMultiValueMap(), queryParams: Map<String, Any> = mapOf()): ResultActions {
        return mockMvc
            .perform(
                MockMvcRequestBuilders
                    .get(endpoint)
                    .headers(HttpHeaders(headers))
            )
    }
}