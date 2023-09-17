package com.unqueam.gamingplatform.core.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GenreTest {

    @Test
    fun `can find a Genre by name`() {
        val genre = Genre.findGenreByName("Arcade")

        assert(genre == Genre.ARCADE)
        assert(genre != Genre.ACTION)
    }

    @Test
    fun `when find a Genre by a name that doesn't match with any genre, it throws exception`() {
        val name = "anyName"
        val exception = assertThrows<IllegalArgumentException> { Genre.findGenreByName(name) }
        assert(exception.message == "No existe un género con el nombre $name")
    }
}
