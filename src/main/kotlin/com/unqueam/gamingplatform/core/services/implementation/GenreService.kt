package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.application.dtos.GenreOutput
import com.unqueam.gamingplatform.core.domain.Genre
import com.unqueam.gamingplatform.core.services.IGenreService

class GenreService : IGenreService {
    override fun fetchGenres(): List<GenreOutput> {
        return Genre.values().map { GenreOutput(it.name, it.spanishName, it.englishName) }
    }
}