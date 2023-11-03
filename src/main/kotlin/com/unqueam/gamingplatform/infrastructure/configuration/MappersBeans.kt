package com.unqueam.gamingplatform.infrastructure.configuration

import com.unqueam.gamingplatform.core.mapper.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class MappersBeans {

    @Bean
    fun gameMapper(): GameMapper {
        return GameMapper()
    }

    @Bean
    fun favoritesGamesMapper(gameMapper: GameMapper): FavoritesGamesMapper {
        return FavoritesGamesMapper(gameMapper)
    }

    @Bean
    fun authMapper(passwordEncoder: PasswordEncoder): AuthMapper {
        return AuthMapper(passwordEncoder)
    }

    @Bean
    fun fileMapper(): FileMapper {
        return FileMapper()
    }

    @Bean
    fun bannerMapper(fileMapper: FileMapper): BannerMapper {
        return BannerMapper(fileMapper)
    }

    @Bean
    fun userMapper(): UserMapper {
        return UserMapper()
    }

    @Bean
    fun commentMapper(): CommentMapper {
        return CommentMapper()
    }


    @Bean
    fun requestToBeDeveloperMapper(): RequestToBeDeveloperMapper {
        return RequestToBeDeveloperMapper()
    }
}