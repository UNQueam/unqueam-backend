package com.unqueam.gamingplatform.core.helper

import com.unqueam.gamingplatform.application.exception.InvalidSignUpPasswordFormatException

private const val REGEX = "^(?=.*[A-Z]).{8,}\$"

class PasswordFormatValidator : IPasswordFormatValidator {

    override fun validateConstraints(password: String) {
        if (!Regex(REGEX).matches(password)) {
            throw InvalidSignUpPasswordFormatException("La contraseña debe contener al menos 8 caracteres y una mayuscula.")
        }
    }
}