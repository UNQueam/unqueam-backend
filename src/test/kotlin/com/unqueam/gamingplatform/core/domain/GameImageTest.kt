package com.unqueam.gamingplatform.core.domain

import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

private const val ID: Long = 1L
private const val URL: String = "url.com"

class GameImageTest {

    @Test
    fun `a game image cannot be instantiated with blank url`() {
        Assertions.assertThatThrownBy { GameImage(ID, StringUtils.EMPTY)}
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("An Image must have a url.")
    }

    @Test
    fun `a game image is created correctly`() {
        val gameImage = GameImage(ID, URL)

        Assertions.assertThat(gameImage.id).isEqualTo(ID)
        Assertions.assertThat(gameImage.url).isEqualTo(URL)
    }

}

