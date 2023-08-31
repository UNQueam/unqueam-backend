package com.unqueam.gamingplatform.infrastructure.configuration

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UtilsBeans {

    @Bean
    fun objectMapper() : ObjectMapper {
        return ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
    }
}