package com.unqueam.gamingplatform.core.helper

interface IPasswordFormatValidator {

    fun validateConstraints(password: String, errorsMap: MutableMap<String, List<String>>)
}