package com.unqueam.gamingplatform.core.domain

import java.util.Optional

enum class Genre(val englishName: String, val spanishName: String) {

    ACTION("Action", "Acción"),
    ADVENTURE("Adventure", "Aventura"),
    RPG("Role Playing Game (RPG)", "Juego de Rol (RPG)"),
    FPS("First-Person Shooter (FPS)", "Disparos en Primera Persona (FPS)"),
    THIRD_PERSON_SHOOTER("Third-Person Shooter", "Disparos en Tercera Persona"),
    STRATEGY("Strategy", "Estrategia"),
    SIMULATION("Simulation", "Simulación"),
    SPORTS("Sports", "Deportes"),
    RACING("Racing", "Carreras"),
    FIGHTING("Fighting", "Lucha"),
    HORROR("Horror", "Terror"),
    PLATFORMER("Platformer", "Plataformas"),
    PUZZLE("Puzzle", "Rompecabezas"),
    OPEN_WORLD("Open World", "Mundo Abierto"),
    MUSIC_RHYTM("Music/Rhythm", "Música/Ritmo"),
    EDUCATIONAL("Educational", "Educativos"),
    CASUAL("Casual", "Casual"),
    ARCADE("Arcade", "Arcade"),
    LIFE_SIMULATION_BUILDING("Life Simulation/Building", "Construcción/Simulación de Vida"),
    STEALTH("Stealth", "Sigilo"),
    SURVIVAL("Survival", "Supervivencia"),
    PSYCHOLOGICAL_HORROR("Psychological Horror", "Terror Psicológico"),
    ROMANCE("Romance", "Romance"),
    SCIENCE_FICTION("Science Fiction", "Ciencia Ficción"),
    FANTASY("Fantasy", "Fantasía"),
    INTERACTIVE_FICTION("Interactive Fiction", "Historia Interactiva"),
    VISUAL_NOVEL("Visual Novel", "Novela Visual"),
    HACK_AND_SLASH("Hack and Slash", "Hack and Slash"),
    TOWER_DEFENSE("Tower Defense", "Tower Defense"),
    CARD_GAMES("Card Games", "Juegos de Cartas"),
    BOARD_GAMES("Board Games", "Juegos de Mesa"),
    FLIGHT_COMBAT("Flight Combat", "Combate Aéreo"),
    HUNTING_AND_FISHING("Hunting and Fishing", "Caza y Pesca"),
    SPACE_EXPLORATION("Space Exploration", "Exploración Espacial"),
    MANAGEMENT_GAMES("Management Games", "Juegos de Gestión"),
    MARTIAL_ARTS("Martial Arts", "Artes Marciales"),
    PLATFORM_PUZZLE("Platform Puzzle", "Rompecabezas de Plataformas"),
    CITY_BUILDING("City Building", "Construcción de Ciudades"),
    PET_SIMULATION("Pet Simulation", "Crianza de Mascotas"),
    TANK_COMBAT("Tank Combat", "Combate de Tanques"),
    VIRTUAL_BOARD_GAMES("Virtual Board Games", "Juegos de Mesa Virtuales"),
    WORD_GAMES("Word Games", "Juegos de Palabras"),
    CHESS_GAMES("Chess Games", "Juegos de Ajedrez"),
    MYSTERY("Mystery", "Misterio"),
    WESTERN("Western", "Western"),
    HISTORICAL("Historical", "Género Histórico"),
    INTERACTIVE_POETRY("Interactive Poetry", "Poesía Interactiva"),
    TIME_TRAVEL("Time Travel", "Juegos de Viaje en el Tiempo"),
    GRAPHIC_NOVEL("Graphic Novel", "Novela Gráfica"),
    URBAN_EXPLORATION("Urban Exploration", "Exploración Urbana");

    companion object {
        fun findGenreByName(name: String): Genre {
            val optionalGenre = Optional.ofNullable(values().find { it.englishName == name || it.spanishName == name })
            return optionalGenre.orElseThrow { IllegalArgumentException("No existe un género con el nombre $name") }
        }
    }
}