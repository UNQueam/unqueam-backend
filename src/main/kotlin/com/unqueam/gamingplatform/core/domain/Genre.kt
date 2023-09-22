package com.unqueam.gamingplatform.core.domain

import java.util.Optional

enum class Genre(val id: Long, val englishName: String, val spanishName: String) {

    ACTION(1, "Action", "Acción"),
    ADVENTURE(2, "Adventure", "Aventura"),
    RPG(3, "Role Playing Game (RPG)", "Juego de Rol (RPG)"),
    FPS(4, "First-Person Shooter (FPS)", "Disparos en Primera Persona (FPS)"),
    THIRD_PERSON_SHOOTER(5, "Third-Person Shooter", "Disparos en Tercera Persona"),
    STRATEGY(6, "Strategy", "Estrategia"),
    SIMULATION(7, "Simulation", "Simulación"),
    SPORTS(8, "Sports", "Deportes"),
    RACING(9, "Racing", "Carreras"),
    FIGHTING(10, "Fighting", "Lucha"),
    HORROR(11, "Horror", "Terror"),
    PLATFORMER(12, "Platformer", "Plataformas"),
    PUZZLE(13, "Puzzle", "Rompecabezas"),
    OPEN_WORLD(14, "Open World", "Mundo Abierto"),
    MUSIC_RHYTM(15, "Music/Rhythm", "Música/Ritmo"),
    EDUCATIONAL(16, "Educational", "Educativos"),
    CASUAL(17, "Casual", "Casual"),
    ARCADE(18, "Arcade", "Arcade"),
    LIFE_SIMULATION_BUILDING(19, "Life Simulation/Building", "Construcción/Simulación de Vida"),
    STEALTH(20, "Stealth", "Sigilo"),
    SURVIVAL(21, "Survival", "Supervivencia"),
    PSYCHOLOGICAL_HORROR(22, "Psychological Horror", "Terror Psicológico"),
    ROMANCE(23, "Romance", "Romance"),
    SCIENCE_FICTION(24, "Science Fiction", "Ciencia Ficción"),
    FANTASY(25, "Fantasy", "Fantasía"),
    INTERACTIVE_FICTION(26, "Interactive Fiction", "Historia Interactiva"),
    VISUAL_NOVEL(27, "Visual Novel", "Novela Visual"),
    HACK_AND_SLASH(28, "Hack and Slash", "Hack and Slash"),
    TOWER_DEFENSE(29, "Tower Defense", "Tower Defense"),
    CARD_GAMES(30, "Card Games", "Juegos de Cartas"),
    BOARD_GAMES(31, "Board Games", "Juegos de Mesa"),
    FLIGHT_COMBAT(32, "Flight Combat", "Combate Aéreo"),
    HUNTING_AND_FISHING(33, "Hunting and Fishing", "Caza y Pesca"),
    SPACE_EXPLORATION(34, "Space Exploration", "Exploración Espacial"),
    MANAGEMENT_GAMES(35, "Management Games", "Juegos de Gestión"),
    MARTIAL_ARTS(36, "Martial Arts", "Artes Marciales"),
    PLATFORM_PUZZLE(37, "Platform Puzzle", "Rompecabezas de Plataformas"),
    CITY_BUILDING(38, "City Building", "Construcción de Ciudades"),
    PET_SIMULATION(39, "Pet Simulation", "Crianza de Mascotas"),
    TANK_COMBAT(40, "Tank Combat", "Combate de Tanques"),
    VIRTUAL_BOARD_GAMES(41, "Virtual Board Games", "Juegos de Mesa Virtuales"),
    WORD_GAMES(42, "Word Games", "Juegos de Palabras"),
    CHESS_GAMES(43, "Chess Games", "Juegos de Ajedrez"),
    MYSTERY(44, "Mystery", "Misterio"),
    WESTERN(45, "Western", "Western"),
    HISTORICAL(46, "Historical", "Género Histórico"),
    INTERACTIVE_POETRY(47, "Interactive Poetry", "Poesía Interactiva"),
    TIME_TRAVEL(48, "Time Travel", "Juegos de Viaje en el Tiempo"),
    GRAPHIC_NOVEL(49, "Graphic Novel", "Novela Gráfica"),
    URBAN_EXPLORATION(50, "Urban Exploration", "Exploración Urbana"),
    PVP(51, "Player VS Player (PVP)", "Jugador contra Jugador (JcJ)");

    companion object {
        fun findGenre(name: String): Genre {
            return try {
                val genreByEnum = Genre.valueOf(name)
                genreByEnum
            } catch (exception : IllegalArgumentException) {
                findGenreByName(name)
            }
        }

        private fun findGenreByName(name: String): Genre {
            val genreName = name.lowercase()
            val optionalGenre = Optional.ofNullable(
                values().find { it.englishName.lowercase() == genreName || it.spanishName.lowercase() == genreName }
            )
            return optionalGenre.orElseThrow { IllegalArgumentException("No existe un género con el nombre $name") }
        }
    }
}