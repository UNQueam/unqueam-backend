package com.unqueam.gamingplatform.application.http

import com.unqueam.gamingplatform.application.dtos.GenreOutput
import com.unqueam.gamingplatform.core.services.IGenreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(API.ENDPOINT_GENRES)
class GenreController {

    private final val genreService: IGenreService

    @Autowired
    constructor(genreService: IGenreService) {
        this.genreService = genreService
    }

    @GetMapping
    fun fetchGenres() : ResponseEntity<List<GenreOutput>> {
        val genres: List<GenreOutput> = genreService.fetchGenres()
        return ResponseEntity.status(HttpStatus.OK).body(genres)
    }
}