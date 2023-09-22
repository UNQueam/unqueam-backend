package com.unqueam.gamingplatform.infrastructure.configuration

import com.unqueam.gamingplatform.core.helper.IPasswordFormatValidator
import com.unqueam.gamingplatform.core.helper.PasswordFormatValidator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HelperBeans {

    @Bean
    fun passwordFormatValidator(): IPasswordFormatValidator {
        return PasswordFormatValidator()
    }
}