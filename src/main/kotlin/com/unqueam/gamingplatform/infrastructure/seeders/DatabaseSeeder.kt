package com.unqueam.gamingplatform.infrastructure.seeders

import com.unqueam.gamingplatform.application.dtos.DeveloperGameInput
import com.unqueam.gamingplatform.application.dtos.GameImageInput
import com.unqueam.gamingplatform.application.dtos.GameRequest
import com.unqueam.gamingplatform.application.dtos.GenreInput
import com.unqueam.gamingplatform.application.http.GetHiddenGamesParam
import com.unqueam.gamingplatform.core.domain.*
import com.unqueam.gamingplatform.core.services.IGameService
import com.unqueam.gamingplatform.infrastructure.persistence.UserRepository
import jakarta.transaction.Transactional
import org.hibernate.internal.util.collections.CollectionHelper.listOf
import org.hibernate.internal.util.collections.CollectionHelper.setOf
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*

@Component
class DatabaseSeeder {

    private final val gameService: IGameService
    private final val userRepository: UserRepository
    private final val passwordEncoder: PasswordEncoder

    @Autowired
    constructor(aGameService: IGameService, userRepository : UserRepository, passwordEncoder: PasswordEncoder) {
        this.gameService = aGameService
        this.userRepository = userRepository
        this.passwordEncoder = passwordEncoder
    }

    @Transactional
    @EventListener
    fun seed(event: ContextRefreshedEvent) {
        val userHulk: PlatformUser = loadUsers().get(2)
        if (gameService.fetchGames(Optional.empty(), GetHiddenGamesParam(true)).isEmpty()) {
            createGames().forEach { gameService.publishGame(it, userHulk) }
            loadViewsForGameWithId("bg-3", 18)
            loadViewsForGameWithId("ow-1", 70)
            loadViewsForGameWithId("ow-2", 110)
            loadViewsForGameWithId("ow-3", 14)
        }

    }

    private fun loadUsers(): List<PlatformUser> {
        val adminn: PlatformUser = PlatformUser(null, "admin.n", passwordEncoder.encode("admin"), "nicolas.demaio19@gmail.com", Role.ADMIN)
        val adminj: PlatformUser = PlatformUser(null, "admin.j", passwordEncoder.encode("admin"), "trejojulian998@gmail.com", Role.ADMIN)

        val user1: PlatformUser = PlatformUser(null, "hulk", passwordEncoder.encode("hulk123"), "hulk@gmail.com", Role.DEVELOPER)
        val user2: PlatformUser = PlatformUser(null, "spider_man", passwordEncoder.encode("spider_man123"), "spider_man@gmail.com", Role.DEVELOPER)
        val user3: PlatformUser = PlatformUser(null, "ant_man", passwordEncoder.encode("ant_man123"), "ant_man@gmail.com", Role.USER)
        val user4: PlatformUser = PlatformUser(null, "falcon", passwordEncoder.encode("falcon123"), "falcon@gmail.com", Role.USER)

        return userRepository.saveAll(mutableListOf(adminn, adminj, user1, user2, user3, user4))
    }

    private fun createGames(): List<GameRequest> {

        return listOf(
            createGame(
                "The girl in the forest",
                "https://i.pinimg.com/1200x/aa/7b/be/aa7bbe9f794a45c7a947a96e9bdf2a13.jpg",
                "Juego plataformero desarrollado durante la cursada de 2021 de la materia de introducción" +
                        "al desarrollo de videojuegos. Eres una niña que intenta escapar de un bosque muy peligroso.",
                "https://trejojulian.github.io/entregas-ipv-trejo-julian/",
                LocalDate.now(),
                setOf(DeveloperGameInput("Julian")),
                setOf(
                    GameImageInput("https://media.discordapp.net/attachments/1016812465859866667/1148765153127579788/image.png?width=1162&height=670"),
                    GameImageInput("https://cdn.discordapp.com/attachments/1016812465859866667/1148765252318658680/image.png")
                ),
                setOf(GenreInput(Genre.ARCADE.name), GenreInput(Genre.PLATFORMER.name)),
                "Delta software",
                "Girl in the forest"
            ),
            createGame(
                "Klaus mision rescate",
                "https://raw.githubusercontent.com/IntroPV/IntroPV.github.io/master/docs/proyectos_previos/imagenes/2020s2-grupoD-1.png",
                "Eres klaus y tienes que rescatar a tu compañero canino de las garras del mal. Utiliza a tus vasallos para abrirte paso " +
                        "ante las peligrosas cavernas.",
                "https://mendezigna.github.io/klaus-mision-rescate/",
                LocalDate.now(),
                setOf(DeveloperGameInput("Anonymous"), DeveloperGameInput("Anonymous"), DeveloperGameInput("Anonymous")),
                setOf(
                    GameImageInput("https://raw.githubusercontent.com/IntroPV/IntroPV.github.io/master/docs/proyectos_previos/imagenes/2020s2-grupoD-2.png"),
                ),
                setOf(GenreInput(Genre.PUZZLE.name), GenreInput(Genre.PLATFORMER.name)),
                "Anonymous",
                "Klaus"
            ),
            createGame(
                "Baldurs gate 3",
                "https://image.api.playstation.com/vulcan/ap/rnd/202302/2321/ba706e54d68d10a0eb6ab7c36cdad9178c58b7fb7bb03d28.png",
                "Representa la tercera entrega principal de la serie Baldur's Gate, basada en el sistema de juego de rol de mesa Dungeons & Dragons.",
                "https://trejojulian.github.io/entregas-ipv-trejo-julian/",
                LocalDate.of(2023, 8, 3),
                setOf(DeveloperGameInput("Bert"), DeveloperGameInput("Joachim")),
                setOf(
                    GameImageInput("https://cdn.cloudflare.steamstatic.com/steam/apps/1086940/ss_c73bc54415178c07fef85f54ee26621728c77504.1920x1080.jpg?t=1692294127") ,
                    GameImageInput("https://oyster.ignimgs.com/mediawiki/apis.ign.com/baldurs-gate-3/e/e5/BG3_Combat_Guide_-_Initiative.png"),
                ),
                setOf(GenreInput(Genre.RPG.name), GenreInput(Genre.ACTION.name)),
                "Larian Studios",
                "BG 3"
            ),
            createGame(
                "Overwatch 2",
                "https://image.api.playstation.com/vulcan/ap/rnd/202308/1002/1c63f7e89a8010eaec68d2dd622b42d3f2290e44e1d8168e.png",
                "Overwatch 2 es un hero shooter, donde los jugadores se dividen en dos equipos y seleccionan uno de los más de 30 personajes héroes establecidos. Los personajes se dividen en 3 clases: daño, apoyo, y tanque.",
                "https://trejojulian.github.io/entregas-ipv-trejo-julian/",
                LocalDate.of(2023, 5, 23),
                setOf(DeveloperGameInput("Jeff"), DeveloperGameInput("Haim")),
                setOf(
                    GameImageInput("https://dbknews.s3.amazonaws.com/uploads/2022/05/ow_gp.png"),
                    GameImageInput("https://images0.persgroep.net/rcs/FkDXAAiEdHTHCboeL_g4LMssSHw/diocontent/221610057/_fitwidth/694/?appId=21791a8992982cd8da851550a453bd7f&quality=0.8"),
                    GameImageInput("https://blog.latam.playstation.com/tachyon/sites/3/2022/06/3d2d01626430e2ee9117d81b834970fa6242e10f.jpg")
                ),
                setOf(GenreInput(Genre.ACTION.name)),
                "Blizzard",
                "Ow 1"
            ),
            createGame(
                "Sea of Stars",
                "https://sm.ign.com/t/ign_it/screenshot/default/sea-of-stars-2020-03-19-20-019-1_vy4y.1280.jpg",
                "Overwatch 2 es un hero shooter, donde los jugadores se dividen en dos equipos y seleccionan uno de los más de 30 personajes héroes establecidos. Los personajes se dividen en 3 clases: daño, apoyo, y tanque.",
                "https://trejojulian.github.io/entregas-ipv-trejo-julian/",
                LocalDate.of(2023, 5, 23),
                setOf(DeveloperGameInput("Jeff"), DeveloperGameInput("Haim")),
                setOf(
                    GameImageInput("https://dbknews.s3.amazonaws.com/uploads/2022/05/ow_gp.png"),
                    GameImageInput("https://images0.persgroep.net/rcs/FkDXAAiEdHTHCboeL_g4LMssSHw/diocontent/221610057/_fitwidth/694/?appId=21791a8992982cd8da851550a453bd7f&quality=0.8"),
                    GameImageInput("https://blog.latam.playstation.com/tachyon/sites/3/2022/06/3d2d01626430e2ee9117d81b834970fa6242e10f.jpg")
                ),
                setOf(GenreInput(Genre.ACTION.name)),
                "Blizzard",
                "Ow 2"
            ),
            createGame(
                "Rocket League",
                "https://upload.wikimedia.org/wikipedia/commons/e/e0/Rocket_League_coverart.jpg",
                "Overwatch 2 es un hero shooter, donde los jugadores se dividen en dos equipos y seleccionan uno de los más de 30 personajes héroes establecidos. Los personajes se dividen en 3 clases: daño, apoyo, y tanque.",
                "https://trejojulian.github.io/entregas-ipv-trejo-julian/",
                LocalDate.of(2023, 5, 23),
                setOf(DeveloperGameInput("Jeff"), DeveloperGameInput("Haim")),
                setOf(
                    GameImageInput("https://dbknews.s3.amazonaws.com/uploads/2022/05/ow_gp.png"),
                    GameImageInput("https://images0.persgroep.net/rcs/FkDXAAiEdHTHCboeL_g4LMssSHw/diocontent/221610057/_fitwidth/694/?appId=21791a8992982cd8da851550a453bd7f&quality=0.8"),
                    GameImageInput("https://blog.latam.playstation.com/tachyon/sites/3/2022/06/3d2d01626430e2ee9117d81b834970fa6242e10f.jpg")
                ),
                setOf(GenreInput(Genre.ACTION.name)),
                "Blizzard",
                "Ow 3"
            ),
            createGame(
                "Warcraft 3",
                "https://m.media-amazon.com/images/M/MV5BMDZlMDE0ZTEtNGVkNS00OTY2LWExMDctMjE2MGZkYTBkNzgyXkEyXkFqcGdeQXVyNjExODE1MDc@._V1_.jpg",
                "Overwatch 2 es un hero shooter, donde los jugadores se dividen en dos equipos y seleccionan uno de los más de 30 personajes héroes establecidos. Los personajes se dividen en 3 clases: daño, apoyo, y tanque.",
                "https://trejojulian.github.io/entregas-ipv-trejo-julian/",
                LocalDate.of(2023, 5, 23),
                setOf(DeveloperGameInput("Jeff"), DeveloperGameInput("Haim")),
                setOf(
                    GameImageInput("https://dbknews.s3.amazonaws.com/uploads/2022/05/ow_gp.png"),
                    GameImageInput("https://images0.persgroep.net/rcs/FkDXAAiEdHTHCboeL_g4LMssSHw/diocontent/221610057/_fitwidth/694/?appId=21791a8992982cd8da851550a453bd7f&quality=0.8"),
                    GameImageInput("https://blog.latam.playstation.com/tachyon/sites/3/2022/06/3d2d01626430e2ee9117d81b834970fa6242e10f.jpg")
                ),
                setOf(GenreInput(Genre.ACTION.name)),
                "Blizzard",
                "Ow 4"
            ),
            createGame(
                "Portal 2",
                "https://media.vandal.net/m/8775/20101225101630_1.jpg",
                "Overwatch 2 es un hero shooter, donde los jugadores se dividen en dos equipos y seleccionan uno de los más de 30 personajes héroes establecidos. Los personajes se dividen en 3 clases: daño, apoyo, y tanque.",
                "https://trejojulian.github.io/entregas-ipv-trejo-julian/",
                LocalDate.of(2023, 5, 23),
                setOf(DeveloperGameInput("Jeff"), DeveloperGameInput("Haim")),
                setOf(
                    GameImageInput("https://dbknews.s3.amazonaws.com/uploads/2022/05/ow_gp.png"),
                    GameImageInput("https://images0.persgroep.net/rcs/FkDXAAiEdHTHCboeL_g4LMssSHw/diocontent/221610057/_fitwidth/694/?appId=21791a8992982cd8da851550a453bd7f&quality=0.8"),
                    GameImageInput("https://blog.latam.playstation.com/tachyon/sites/3/2022/06/3d2d01626430e2ee9117d81b834970fa6242e10f.jpg")
                ),
                setOf(GenreInput(Genre.ACTION.name)),
                "Blizzard",
                "Ow 5"
            ),
            createGame(
                "Nier Automata",
                "https://cloudfront-us-east-1.images.arcpublishing.com/infobae/EL3373PPRZA4TL6TOWFGCMYYWA.jpg",
                "Overwatch 2 es un hero shooter, donde los jugadores se dividen en dos equipos y seleccionan uno de los más de 30 personajes héroes establecidos. Los personajes se dividen en 3 clases: daño, apoyo, y tanque.",
                "https://trejojulian.github.io/entregas-ipv-trejo-julian/",
                LocalDate.of(2023, 5, 23),
                setOf(DeveloperGameInput("Jeff"), DeveloperGameInput("Haim")),
                setOf(
                    GameImageInput("https://dbknews.s3.amazonaws.com/uploads/2022/05/ow_gp.png"),
                    GameImageInput("https://images0.persgroep.net/rcs/FkDXAAiEdHTHCboeL_g4LMssSHw/diocontent/221610057/_fitwidth/694/?appId=21791a8992982cd8da851550a453bd7f&quality=0.8"),
                    GameImageInput("https://blog.latam.playstation.com/tachyon/sites/3/2022/06/3d2d01626430e2ee9117d81b834970fa6242e10f.jpg")
                ),
                setOf(GenreInput(Genre.ACTION.name)),
                "Blizzard",
                "Ow 6"
            ),
            createGame(
                "Dark Souls",
                "https://img.redbull.com/images/c_limit,w_1500,h_1000,f_auto,q_auto/redbullcom/2018/01/17/9e235bdc-0636-4131-a8f3-91a7446899fb/dark_souls_nintendo-switch",
                "Overwatch 2 es un hero shooter, donde los jugadores se dividen en dos equipos y seleccionan uno de los más de 30 personajes héroes establecidos. Los personajes se dividen en 3 clases: daño, apoyo, y tanque.",
                "https://trejojulian.github.io/entregas-ipv-trejo-julian/",
                LocalDate.of(2023, 5, 23),
                setOf(DeveloperGameInput("Jeff"), DeveloperGameInput("Haim")),
                setOf(
                    GameImageInput("https://dbknews.s3.amazonaws.com/uploads/2022/05/ow_gp.png"),
                    GameImageInput("https://images0.persgroep.net/rcs/FkDXAAiEdHTHCboeL_g4LMssSHw/diocontent/221610057/_fitwidth/694/?appId=21791a8992982cd8da851550a453bd7f&quality=0.8"),
                    GameImageInput("https://blog.latam.playstation.com/tachyon/sites/3/2022/06/3d2d01626430e2ee9117d81b834970fa6242e10f.jpg")
                ),
                setOf(GenreInput(Genre.ACTION.name)),
                "Blizzard",
                "Ow 7",
                true
            ),
            createGame(
                "KOTOR 2",
                "https://cdn.cdkeys.com/700x700/media/catalog/product/c/o/co2228_13_.jpg",
                "Overwatch 2 es un hero shooter, donde los jugadores se dividen en dos equipos y seleccionan uno de los más de 30 personajes héroes establecidos. Los personajes se dividen en 3 clases: daño, apoyo, y tanque.",
                "https://trejojulian.github.io/entregas-ipv-trejo-julian/",
                LocalDate.of(2023, 5, 23),
                setOf(DeveloperGameInput("Jeff"), DeveloperGameInput("Haim")),
                setOf(
                    GameImageInput("https://dbknews.s3.amazonaws.com/uploads/2022/05/ow_gp.png"),
                    GameImageInput("https://images0.persgroep.net/rcs/FkDXAAiEdHTHCboeL_g4LMssSHw/diocontent/221610057/_fitwidth/694/?appId=21791a8992982cd8da851550a453bd7f&quality=0.8"),
                    GameImageInput("https://blog.latam.playstation.com/tachyon/sites/3/2022/06/3d2d01626430e2ee9117d81b834970fa6242e10f.jpg")
                ),
                setOf(GenreInput(Genre.ACTION.name)),
                "Blizzard",
                    "Kotor",
                true
            ),
        )
    }

    private fun createGame(
        name: String,
        logoUrl: String,
        description: String,
        linkToGame: String,
        releaseDate: LocalDate,
        developers: Set<DeveloperGameInput>,
        images: Set<GameImageInput>,
        genres: Set<GenreInput>,
        developmentTeam: String,
        alias: String,
        isHidden: Boolean? = false,

    ): GameRequest {
        return GameRequest(
            name,
            logoUrl,
            description,
            linkToGame,
            releaseDate,
            developers,
            images,
            genres,
            developmentTeam,
            isHidden!!,
            alias
        )
    }

    private fun loadViewsForGameWithId(gameAlias: String, views: Int) {
        for (i in 1..views) {
            gameService.fetchGameByAlias(gameAlias)
        }
    }

}