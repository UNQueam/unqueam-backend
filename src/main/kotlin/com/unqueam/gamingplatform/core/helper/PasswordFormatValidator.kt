package com.unqueam.gamingplatform.core.helper

import com.unqueam.gamingplatform.core.exceptions.Exceptions

private const val REGEX = "^(?=.*[A-Z]).{8,}\$"

class PasswordFormatValidator : IPasswordFormatValidator {

    override fun validateConstraints(password: String, errorsMap: MutableMap<String, List<String>>) {
        if (!Regex(REGEX).matches(password))
            errorsMap["password"] = listOf(Exceptions.PASSWORD_MUST_HAVE_AT_LEAST_8_CHARACTERS_AND_A_CAPITAL_LETTER)
    }
}