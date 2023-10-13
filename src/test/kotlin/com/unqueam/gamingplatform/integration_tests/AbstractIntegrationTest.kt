package com.unqueam.gamingplatform.integration_tests

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.unqueam.gamingplatform.UnqueamApplication
import com.unqueam.gamingplatform.application.auth.CustomUserDetails
import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.infrastructure.configuration.jwt.JwtAuthenticationFilter
import com.unqueam.gamingplatform.utils.UserTestResource
import org.apache.commons.lang3.StringUtils
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
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
@ActiveProfiles("test")
abstract class AbstractIntegrationTest {

    @Autowired
    private val webApplicationContext: WebApplicationContext? = null

    protected lateinit var mockMvc: MockMvc
    protected lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup(@Autowired objectMapper: ObjectMapper) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext!!).build()
        this.objectMapper = objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false)
    }

    @AfterEach
    fun tearDown() {
        SecurityContextHolder.clearContext()
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

    fun getTo(endpoint: String, headers: MultiValueMap<String, String> = LinkedMultiValueMap(), queryParams: MultiValueMap<String, String> = LinkedMultiValueMap()): ResultActions {
        return mockMvc
            .perform(
                MockMvcRequestBuilders
                    .get(endpoint)
                    .headers(HttpHeaders(headers))
                    .params(queryParams)
            )
    }

    fun deleteTo(endpoint: String, headers: MultiValueMap<String, String> = LinkedMultiValueMap(), queryParams: MultiValueMap<String, String> = LinkedMultiValueMap()): ResultActions {
        return mockMvc
            .perform(
                MockMvcRequestBuilders
                    .delete(endpoint)
                    .headers(HttpHeaders(headers))
                    .params(queryParams)
            )
    }

    fun mockAuthenticatedUser(user: PlatformUser) {
        val userDetails = CustomUserDetails(user)
        val authentication: Authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        SecurityContextHolder.getContext().authentication = authentication
    }
}