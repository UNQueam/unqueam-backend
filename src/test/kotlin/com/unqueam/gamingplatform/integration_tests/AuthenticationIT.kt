package com.unqueam.gamingplatform.integration_tests

import com.unqueam.gamingplatform.application.dtos.SignInRequest
import com.unqueam.gamingplatform.application.dtos.SignUpRequest
import com.unqueam.gamingplatform.application.http.API
import com.unqueam.gamingplatform.core.exceptions.Exceptions
import com.unqueam.gamingplatform.integration_tests.data_loader.UserDataLoader
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
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
     */

    private lateinit var userDataLoader: UserDataLoader

    @BeforeEach
    fun setup(@Autowired userDataLoader: UserDataLoader) {
        this.userDataLoader = userDataLoader
    }

    @Test
    fun `1 - User sign-in with valid requests, Success 200`() {
        userDataLoader.loadNewUser(USERNAME, PASSWORD)

        val signInRequest = asJson(SignInRequest(USERNAME, PASSWORD))

        postTo(API.ENDPOINT_AUTH + "/signIn", signInRequest)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value(USERNAME))
            .andExpect(jsonPath("$.auth_token").isNotEmpty())
    }

    @Test
    fun `2 - User sign-in but username is not registered, BadRequest 400`() {
        val signInRequest = asJson(SignInRequest(USERNAME, PASSWORD))

        postTo(API.ENDPOINT_AUTH + "/signIn", signInRequest)
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(Exceptions.USER_OR_PASSWORD_ARE_INCORRECT))
    }

    @Test
    fun `3 - User sign-in with registered username but the password does not match, BadRequest 400`() {
        userDataLoader.loadNewUser(USERNAME, PASSWORD)

        val signInRequest = asJson(SignInRequest(USERNAME, PASSWORD + "123"))

        postTo(API.ENDPOINT_AUTH + "/signIn", signInRequest)
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(Exceptions.USER_OR_PASSWORD_ARE_INCORRECT))
    }

    @Test
    fun `4 - User sign-up, Created 201`() {
        val signUpRequest = asJson(SignUpRequest(USERNAME, EMAIL, PASSWORD))

        postTo(API.ENDPOINT_AUTH + "/signUp", signUpRequest)
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.username").value(USERNAME))
            .andExpect(jsonPath("$.auth_token").isNotEmpty())
    }

    @Test
    fun `5 - User try to sign-up with username in use, BadRequest 400`() {
        userDataLoader.loadNewUser(USERNAME, PASSWORD)

        val signUpRequest = asJson(SignUpRequest(USERNAME, EMAIL, PASSWORD))

        postTo(API.ENDPOINT_AUTH + "/signUp", signUpRequest)
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors.username").value(Exceptions.THE_USERNAME_IS_ALREADY_IN_USE))
    }

    @Test
    fun `6 - User try to sign-up with email in use, BadRequest 400`() {
        userDataLoader.loadNewUser(USERNAME, PASSWORD, email = EMAIL)

        val signUpRequest = asJson(SignUpRequest(USERNAME, EMAIL, PASSWORD))

        postTo(API.ENDPOINT_AUTH + "/signUp", signUpRequest)
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors.email").value(Exceptions.THE_EMAIL_ADDRESS_IS_ALREADY_IN_USE))
    }

    @Test
    fun `7 - User try to sign-up with password that no contains 8 characters, BadRequest 400`() {
        val signUpRequest = asJson(SignUpRequest(USERNAME, EMAIL, "Passw"))

        postTo(API.ENDPOINT_AUTH + "/signUp", signUpRequest)
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors.password").value(Exceptions.PASSWORD_MUST_HAVE_AT_LEAST_8_CHARACTERS_AND_A_CAPITAL_LETTER))
    }

    @Test
    fun `8 - User logout, Success 200`() {
        val headersMap: MultiValueMap<String, String> = LinkedMultiValueMap()
        headersMap.add("Authorization", "Bearer sometoken12334123")

        getTo(API.ENDPOINT_AUTH + "/logout", headersMap)
            .andExpect(status().isOk())
    }
}