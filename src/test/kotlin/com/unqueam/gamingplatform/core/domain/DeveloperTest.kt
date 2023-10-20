package com.unqueam.gamingplatform.core.domain

import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

private const val ID: Long = 1L
private const val NAME: String = "pepe"


class DeveloperTest {

    @Test
    fun `a developer cannot be instantiated with blankname`() {
        Assertions.assertThatThrownBy { Developer(ID, StringUtils.EMPTY)}
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("A Developer must have a name.")
    }


    @Test
    fun `a developer is created correctly`() {
        val developer = Developer(ID, NAME)

        assertThat(developer.id).isEqualTo(ID)
        assertThat(developer.name).isEqualTo(NAME)
    }

}