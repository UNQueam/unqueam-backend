package com.unqueam.gamingplatform.infrastructure.configuration

import com.unqueam.gamingplatform.core.mapper.AuthMapper
import com.unqueam.gamingplatform.core.mapper.GameMapper
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
    fun authMapper(passwordEncoder: PasswordEncoder): AuthMapper {
        return AuthMapper(passwordEncoder)
    }
}