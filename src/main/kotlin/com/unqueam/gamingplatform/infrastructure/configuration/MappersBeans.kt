package com.unqueam.gamingplatform.infrastructure.configuration

import com.unqueam.gamingplatform.core.mapper.GameMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MappersBeans {

    @Bean
    fun gameMapper(): GameMapper {
        return GameMapper()
    }

}