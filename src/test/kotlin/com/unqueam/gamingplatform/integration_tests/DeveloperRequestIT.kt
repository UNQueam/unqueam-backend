package com.unqueam.gamingplatform.integration_tests

import com.unqueam.gamingplatform.application.dtos.RejectedMessage
import com.unqueam.gamingplatform.application.http.API
import com.unqueam.gamingplatform.infrastructure.configuration.jwt.JwtHelper
import com.unqueam.gamingplatform.integration_tests.data_loader.RequestToBeDeveloperDataLoader
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.LinkedMultiValueMap

private const val REQUEST_ID = 1

class DeveloperRequestIT : AbstractIntegrationTest() {

    private lateinit var requestToBeDeveloperDataLoader: RequestToBeDeveloperDataLoader

    @BeforeEach
    fun setup(@Autowired requestToBeDeveloperDataLoader: RequestToBeDeveloperDataLoader) {
        this.requestToBeDeveloperDataLoader = requestToBeDeveloperDataLoader
    }

    /**
     * Test cases (sorted)
    1. Reject a request to be developer - 201 no content
    2. Reject a request that has been modified - 400 Bad request
    3. Reject a request with ID that not exists - 404 Not found
    4. Approve a request to be developer - 201 no content
    5. Approve a request that has been modified - 400 Bad request
    6. Approve a request with ID that not exists - 404 Not found
    7. Fetch requests to be developer - 200 Ok success
     */

    @Test
    fun `1 - Reject a request to be developer, 201 no content`() {
        val user = userDataLoader.fetchLoadedUser("admin.j")
        val token = buildJwtTokenForUser(user)

        val loadedRequest = requestToBeDeveloperDataLoader.loadRequest()

        val requestBody = asJson(RejectedMessage("Tu no puedes ser developer."))

        putTo(API.ENDPOINT_REQUESTS + "/${loadedRequest.requestId}/reject", requestBody, token)
            .andExpect(status().isNoContent())
            .andReturn()
    }

    @Test
    fun `2 - Reject a request that has been modified, 400 Bad request`() {
        val user = userDataLoader.fetchLoadedUser("admin.j")
        val token = buildJwtTokenForUser(user)

        val loadedRequest = requestToBeDeveloperDataLoader.loadRequest()

        val requestBody = asJson(RejectedMessage("Tu no puedes ser developer."))

        putTo(API.ENDPOINT_REQUESTS + "/${loadedRequest.requestId}/reject", requestBody, token)

        putTo(API.ENDPOINT_REQUESTS + "/${loadedRequest.requestId}/reject", requestBody, token)
            .andExpect(status().isBadRequest())
            .andReturn()
    }

    @Test
    fun `3 - Reject a request with ID that not exists, 404 Not found`() {
        val user = userDataLoader.fetchLoadedUser("admin.j")
        val token = buildJwtTokenForUser(user)

        val requestBody = asJson(RejectedMessage("Tu no puedes ser developer."))

        putTo(API.ENDPOINT_REQUESTS + "/${REQUEST_ID}/reject", requestBody, token)
            .andExpect(status().isNotFound())
            .andReturn()
    }

    @Test
    fun `4 - Approve a request to be developer, 201 no content`() {
        val user = userDataLoader.fetchLoadedUser("admin.j")
        val token = buildJwtTokenForUser(user)

        val loadedRequest = requestToBeDeveloperDataLoader.loadRequest()

        putTo(API.ENDPOINT_REQUESTS + "/${loadedRequest.requestId}/approve", "", token)
            .andExpect(status().isNoContent())
            .andReturn()
    }

    @Test
    fun `5 - Approve a request that has been modified, 400 Bad request`() {
        val user = userDataLoader.fetchLoadedUser("admin.j")
        val token = buildJwtTokenForUser(user)

        val loadedRequest = requestToBeDeveloperDataLoader.loadRequest()

        putTo(API.ENDPOINT_REQUESTS + "/${loadedRequest.requestId}/approve", "", token)

        putTo(API.ENDPOINT_REQUESTS + "/${loadedRequest.requestId}/approve", "", token)
            .andExpect(status().isBadRequest())
            .andReturn()
    }

    @Test
    fun `6 - Approve a request with ID that not exists - 404 Not found`() {
        val user = userDataLoader.fetchLoadedUser("admin.j")
        val token = buildJwtTokenForUser(user)

        putTo(API.ENDPOINT_REQUESTS + "/${REQUEST_ID}/approve", "", token)
            .andExpect(status().isNotFound())
            .andReturn()
    }

    @Test
    fun `7 - Fetch requests to be developer - 200 Ok success`() {
        val user = userDataLoader.fetchLoadedUser("admin.j")
        val token = buildJwtTokenForUser(user)

        val headers = LinkedMultiValueMap<String, String>()
        headers.put(JwtHelper.AUTH_HEADER_KEY, mutableListOf(token))

        val loadedRequest = requestToBeDeveloperDataLoader.loadRequest()

        getTo(API.ENDPOINT_REQUESTS, headers = headers).andExpect(status().isOk)
            .andExpect(jsonPath("$[0].request_id").value(loadedRequest.requestId))
            .andReturn()
    }
}