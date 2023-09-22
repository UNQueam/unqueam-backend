package com.unqueam.gamingplatform.core.domain

import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

private const val ID: Long = 1L
private const val FIRST_NAME: String = "pepe"
private const val LAST_NAME: String = "juarez"

class DeveloperTest {

    @Test
    fun `a developer cannot be instantiated with blank first name`() {
        Assertions.assertThatThrownBy { Developer(ID, StringUtils.EMPTY, LAST_NAME)}
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("A Developer must have a first name.")
    }

    @Test
    fun `a developer cannot be instantiated with blank last name`() {
        Assertions.assertThatThrownBy { Developer(ID, FIRST_NAME, StringUtils.EMPTY)}
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("A Developer must have a last name.")
    }

    @Test
    fun `a developer is created correctly`() {
        val developer = Developer(ID, FIRST_NAME, LAST_NAME)

        assertThat(developer.id).isEqualTo(ID)
        assertThat(developer.firstName).isEqualTo(FIRST_NAME)
        assertThat(developer.lastName).isEqualTo(LAST_NAME)
    }

}