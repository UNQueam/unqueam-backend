package com.unqueam.gamingplatform.infrastructure.configuration

import EmailService
import com.unqueam.gamingplatform.infrastructure.configuration.jwt.JwtService
import com.unqueam.gamingplatform.core.helper.IPasswordFormatValidator
import com.unqueam.gamingplatform.core.mapper.*
import com.unqueam.gamingplatform.core.services.*
import com.unqueam.gamingplatform.core.services.implementation.*
import com.unqueam.gamingplatform.infrastructure.persistence.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.thymeleaf.TemplateEngine

@Configuration
class ServicesBeans {

    @Bean
    fun gameService(gameRepository: GameRepository, gameMapper: GameMapper, trackingService: ITrackingService,
                    commentMapper: CommentMapper, commentRepository: CommentRepository): IGameService {
        return GameService(gameRepository, gameMapper, trackingService, commentMapper, commentRepository)
    }

    @Bean
    fun favoriteGamesService(userService: IUserService, favoriteGamesRepository: FavoriteGamesRepository, gamesRepository: GameRepository, favoritesGamesMapper: FavoritesGamesMapper): IFavoriteGamesService {
        return FavoriteGamesService(userService, favoriteGamesRepository, gamesRepository, favoritesGamesMapper)
    }

    @Bean
    fun bannerService(bannerRepository: BannerRepository, bannerMapper: BannerMapper): IBannerService {
        return BannerService(bannerRepository, bannerMapper)
    }

    @Bean
    fun trackingService(trackingEventsRepository: TrackingEventsRepository): ITrackingService {
        return TrackingService(trackingEventsRepository)
    }

    @Bean
    fun genreService(): IGenreService {
        return GenreService()
    }

    @Bean
    fun userService(userRepository: UserRepository): IUserService {
        return UserService(userRepository)
    }

    @Bean
    fun adminService(userRepository: UserRepository, aUserMapper: UserMapper) : IAdminService {
        return AdminService(userRepository, aUserMapper)
    }

    @Bean
    @Profile("prod")
    fun prodEmailService(javaMailSender: JavaMailSender, templateEngine: TemplateEngine): IEmailService {
        return EmailService(javaMailSender, templateEngine)
    }

    @Bean
    @Profile("test")
    fun testEmailService(): IEmailService {
        return FixedEmailService()
    }

    @Bean
    fun authenticationService(
        userService: IUserService,
        authMapper: AuthMapper,
        jwtService: JwtService,
        authenticationManager: AuthenticationManager,
        passwordEncoder: PasswordEncoder,
        passwordFormatValidator: IPasswordFormatValidator) : IAuthenticationService {
        return AuthService(userService, authMapper, jwtService, authenticationManager, passwordEncoder, passwordFormatValidator)
    }

    @Bean
    fun developerService(
        requestToBeDeveloperRepository: RequestToBeDeveloperRepository,
        mapper: RequestToBeDeveloperMapper,
        userRepository: UserRepository,
        emailService: IEmailService): IDeveloperRequestService {
        return DeveloperRequestService(requestToBeDeveloperRepository, mapper, userRepository, emailService)
    }
}