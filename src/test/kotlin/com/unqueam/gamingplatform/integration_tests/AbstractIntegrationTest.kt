package com.unqueam.gamingplatform.integration_tests

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.unqueam.gamingplatform.application.auth.AuthContextHelper
import com.unqueam.gamingplatform.application.auth.CustomUserDetails
import com.unqueam.gamingplatform.core.domain.PlatformUser
import com.unqueam.gamingplatform.infrastructure.configuration.jwt.JwtHelper
import com.unqueam.gamingplatform.infrastructure.configuration.jwt.JwtService
import com.unqueam.gamingplatform.integration_tests.data_loader.UserDataLoader
import io.mockk.every
import io.mockk.mockkObject
import jakarta.persistence.EntityManager
import org.apache.commons.lang3.StringUtils
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = ["classpath:application-test.properties"])
@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@ContextConfiguration
abstract class AbstractIntegrationTest {

    @Autowired
    val mockMvc: MockMvc? = null

    protected lateinit var objectMapper: ObjectMapper
    protected lateinit var jwtService: JwtService
    protected lateinit var userDataLoader: UserDataLoader
    protected lateinit var entityManager: EntityManager

    @BeforeEach
    fun setup(@Autowired objectMapper: ObjectMapper, @Autowired jwtService: JwtService, @Autowired userDataLoader: UserDataLoader, @Autowired entityManager: EntityManager) {
        this.objectMapper = objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false)
        this.jwtService = jwtService
        this.userDataLoader = userDataLoader
        this.entityManager = entityManager
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    fun tearDown() {
        SecurityContextHolder.clearContext()
        this.entityManager.clear();
    }

    fun asJson(anObject: Any): String {
        return objectMapper.writeValueAsString(anObject)
    }

    fun postTo(endpoint: String, bodyContent: String, authenticationToken: String = ""): ResultActions {
        return mockMvc!!
            .perform(
                MockMvcRequestBuilders
                    .post(endpoint)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(JwtHelper.AUTH_HEADER_KEY, authenticationToken)
                    .content(bodyContent)
            )
    }

    fun putTo(endpoint: String, bodyContent: String = StringUtils.EMPTY, authenticationToken: String = ""): ResultActions {
        return mockMvc!!
            .perform(
                MockMvcRequestBuilders
                    .put(endpoint)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(JwtHelper.AUTH_HEADER_KEY, authenticationToken)
                    .content(bodyContent)
            )
    }

    fun getTo(endpoint: String, headers: MultiValueMap<String, String> = LinkedMultiValueMap(), queryParams: MultiValueMap<String, String> = LinkedMultiValueMap()): ResultActions {
        return mockMvc!!
            .perform(
                MockMvcRequestBuilders
                    .get(endpoint)
                    .headers(HttpHeaders(headers))
                    .params(queryParams)
            )
    }

    fun deleteTo(endpoint: String, headers: MultiValueMap<String, String> = LinkedMultiValueMap(), queryParams: MultiValueMap<String, String> = LinkedMultiValueMap()): ResultActions {
        return mockMvc!!
            .perform(
                MockMvcRequestBuilders
                    .delete(endpoint)
                    .headers(HttpHeaders(headers))
                    .params(queryParams)
            )
    }

    fun buildJwtTokenForUser(user: PlatformUser): String {
        mockkObject(AuthContextHelper)
        every { AuthContextHelper.getAuthenticatedUser() } returns user
        return "Bearer " + jwtService.generateToken(CustomUserDetails(user))
    }
}