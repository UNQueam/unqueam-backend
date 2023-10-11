package com.unqueam.gamingplatform.infrastructure.configuration

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class UtilsBeans {

    @Bean
    fun objectMapper() : ObjectMapper {
        return ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            .findAndRegisterModules()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun javaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = "smtp.gmail.com"
        mailSender.port = 587
        mailSender.username = "nicolas.demaio19@gmail.com"
        mailSender.password = "" // Esto es una 'contraseña de aplicacion' (Se llama asi). Se crea una vez unica por cuenta. Hay que leerlo de un archivo
        mailSender.password = emailPassword
        val props = mailSender.javaMailProperties
        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.debug"] = "true"
        return mailSender
    }
}