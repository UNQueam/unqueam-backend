package com.unqueam.gamingplatform.core.helper

import java.time.format.DateTimeFormatter
import java.util.Locale

object DateTimeHelper {

    fun getFormatter(): DateTimeFormatter {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a", Locale.ENGLISH)
    }
}