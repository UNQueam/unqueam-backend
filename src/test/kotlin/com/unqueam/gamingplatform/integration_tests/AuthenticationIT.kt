package com.unqueam.gamingplatform.integration_tests

import com.unqueam.gamingplatform.application.dtos.SignInRequest
import com.unqueam.gamingplatform.application.dtos.SignUpRequest
import com.unqueam.gamingplatform.application.dtos.UserProfileOutput
import com.unqueam.gamingplatform.application.dtos.UserProfileRequest
import com.unqueam.gamingplatform.application.http.API
import com.unqueam.gamingplatform.core.exceptions.Exceptions
import com.unqueam.gamingplatform.infrastructure.configuration.jwt.JwtHelper
import com.unqueam.gamingplatform.utils.GameRequestTestResource
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.util.Assert
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

/**
 * Integration tests for endpoints of authentication
 */
class AuthenticationIT : AbstractIntegrationTest() {

    private val USERNAME: String = "Username"
    private val EMAIL: String = "username@gmail.com"
    private val PASSWORD: String = "Password1"

    /**
     * Test cases (sorted)
    1. signIn - 200 ok success
    2. signIn - 400 username invalid
    3. signIn - 400 password not matches with registered user
    4. signUp - 201 created success
    5. signUp - 400 username already used
    6. signUp - 400 email already used
    7. signUp - 400 password does not satisfy requirements (1 mayus and minim 8 characters)
    8. logout - 200 ok success
    9. signUp - A profile is created when the user is registered
     */

    @Test
    fun `1 - User sign-in with valid requests, Success 200`() {
        userDataLoader.loadNewUser(USERNAME, PASSWORD)

        val signInRequest = asJson(SignInRequest(USERNAME, PASSWORD))

        postTo(API.ENDPOINT_AUTH + "/signIn", signInRequest)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value(USERNAME))
            .andExpect(jsonPath("$.auth_token").isNotEmpty())
            .andReturn()
    }

    @Test
    fun `2 - User sign-in but username is not registered, BadRequest 400`() {
        val signInRequest = asJson(SignInRequest(USERNAME, PASSWORD))

        postTo(API.ENDPOINT_AUTH + "/signIn", signInRequest)
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(Exceptions.USER_OR_PASSWORD_ARE_INCORRECT))
            .andReturn()
    }

    @Test
    fun `3 - User sign-in with registered username but the password does not match, BadRequest 400`() {
        userDataLoader.loadNewUser(USERNAME, PASSWORD)

        val signInRequest = asJson(SignInRequest(USERNAME, PASSWORD + "123"))

        postTo(API.ENDPOINT_AUTH + "/signIn", signInRequest)
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(Exceptions.USER_OR_PASSWORD_ARE_INCORRECT))
            .andReturn()
    }

    @Test
    fun `4 - User sign-up, Created 201`() {
        val signUpRequest = asJson(SignUpRequest(USERNAME, EMAIL, PASSWORD))

        postTo(API.ENDPOINT_AUTH + "/signUp", signUpRequest)
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.username").value(USERNAME))
            .andExpect(jsonPath("$.auth_token").isNotEmpty())
            .andReturn()
    }

    @Test
    fun `5 - User try to sign-up with username in use, BadRequest 400`() {
        userDataLoader.loadNewUser(USERNAME, PASSWORD)

        val signUpRequest = asJson(SignUpRequest(USERNAME, EMAIL, PASSWORD))

        postTo(API.ENDPOINT_AUTH + "/signUp", signUpRequest)
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors.username").value(Exceptions.THE_USERNAME_IS_ALREADY_IN_USE))
            .andReturn()
    }

    @Test
    fun `6 - User try to sign-up with email in use, BadRequest 400`() {
        userDataLoader.loadNewUser(USERNAME, PASSWORD, email = EMAIL)

        val signUpRequest = asJson(SignUpRequest(USERNAME, EMAIL, PASSWORD))

        postTo(API.ENDPOINT_AUTH + "/signUp", signUpRequest)
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors.email").value(Exceptions.THE_EMAIL_ADDRESS_IS_ALREADY_IN_USE))
            .andReturn()
    }

    @Test
    fun `7 - User try to sign-up with password that no contains 8 characters, BadRequest 400`() {
        val signUpRequest = asJson(SignUpRequest(USERNAME, EMAIL, "Passw"))

        postTo(API.ENDPOINT_AUTH + "/signUp", signUpRequest)
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors.password").value(Exceptions.PASSWORD_MUST_HAVE_AT_LEAST_8_CHARACTERS_AND_A_CAPITAL_LETTER))
            .andReturn()
    }

    @Test
    fun `8 - User logout, Success 200`() { // fix
        val user = userDataLoader.loadNewUser(USERNAME, PASSWORD)

        val body = objectMapper.writeValueAsString(SignInRequest(USERNAME, PASSWORD))
        postTo(API.ENDPOINT_AUTH + "/signIn", body, "")

        val headersMap: MultiValueMap<String, String> = LinkedMultiValueMap()
        headersMap.add(JwtHelper.AUTH_HEADER_KEY, buildJwtTokenForUser(user))

        getTo(API.ENDPOINT_AUTH + "/logout", headersMap)
            .andExpect(status().isOk())
            .andReturn()
    }

    @Test
    fun `9 - A profile is created when the user is registered`() {
        val signUpRequest = asJson(SignUpRequest(USERNAME, EMAIL, PASSWORD))

        val signUpResult = postTo(API.ENDPOINT_AUTH + "/signUp", signUpRequest)
                .andReturn()

        val responseBody =  objectMapper.readValue(signUpResult.response.contentAsString, HashMap::class.java)
        val userId = responseBody["user_id"]
        val token: String = responseBody["auth_token"] as String
        val headersMap: MultiValueMap<String, String> = LinkedMultiValueMap()
        headersMap.add(JwtHelper.AUTH_HEADER_KEY, token)

        getTo("/api/users/$userId/profile",
                headersMap)
                .andExpect(status().isOk())
                .andReturn()
    }

    @Test
    fun `10 - A profile can be updated by the owner`() {
        val updateRequest = asJson(UserProfileRequest("Description","An image"))

        val user = userDataLoader.fetchLoadedUser("hulk")
        val userId = user.id

        val token = buildJwtTokenForUser(user)

        putTo("/api/users/$userId/profile", updateRequest, token)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.image_id").value("An image"))
                .andReturn()
    }

    @Test
    fun `11 - A profile can not be updated by someone that is not the owner`() {
        val updateRequest = asJson(UserProfileRequest("Description","An image"))

        val user = userDataLoader.fetchLoadedUser("hulk")
        val userId = user.id
        val anotherUser = userDataLoader.fetchLoadedUser("admin.j")

        val token = buildJwtTokenForUser(anotherUser)

        putTo("/api/users/$userId/profile", updateRequest, token)
                .andExpect(status().isBadRequest())
                .andReturn()
    }


}