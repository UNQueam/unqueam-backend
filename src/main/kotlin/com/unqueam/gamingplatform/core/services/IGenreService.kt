package com.unqueam.gamingplatform.core.services

import com.unqueam.gamingplatform.application.dtos.GenreOutput

interface IGenreService {

    fun fetchGenres(): List<GenreOutput>
}