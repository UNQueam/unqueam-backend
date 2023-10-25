package com.unqueam.gamingplatform.core.helper

import java.util.*

object KebabConverter {
    fun toKebabCase(input: String): String {
        val words = input.split(Regex("\\s+"))

        return words.joinToString("-").lowercase(Locale.getDefault())
    }
}