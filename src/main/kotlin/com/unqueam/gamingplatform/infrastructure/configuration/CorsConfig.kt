package com.unqueam.gamingplatform.infrastructure.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()

        // Permitir todos los orígenes (esto permite CORS desde cualquier origen)
        config.allowedOrigins = listOf("*")

        // Permitir todos los métodos HTTP (GET, POST, PUT, DELETE, etc.)
        config.allowedMethods = listOf("*")

        // Permitir las cabeceras necesarias
        config.allowedHeaders = listOf("*")

        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}