package com.unqueam.gamingplatform.core.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GenreTest {

    @Test
    fun `can find a Genre by name`() {
        val genre = Genre.findGenre("Arcade")

        assert(genre == Genre.ARCADE)
        assert(genre != Genre.ACTION)
    }

    @Test
    fun `can find a Genre by Enum string`() {
        val genre = Genre.findGenre("ARCADE")

        assert(genre == Genre.ARCADE)
    }

    @Test
    fun `when find a Genre by a name that doesn't match with any genre, it throws exception`() {
        val name = "anyName"
        val exception = assertThrows<IllegalArgumentException> { Genre.findGenre(name) }
        assert(exception.message == "No existe un género con el nombre $name")
    }
}
