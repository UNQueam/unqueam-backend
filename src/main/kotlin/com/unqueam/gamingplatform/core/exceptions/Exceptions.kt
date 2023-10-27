package com.unqueam.gamingplatform.core.exceptions

object Exceptions {
    const val THE_USERNAME_IS_ALREADY_IN_USE = "El nombre de usuario ya está en uso"
    const val THE_ALIAS_IS_ALREADY_IN_USE = "El alias del juego ya está en uso"
    const val THE_EMAIL_ADDRESS_IS_ALREADY_IN_USE = "El correo electrónico ya está en uso"
    const val PASSWORD_MUST_HAVE_AT_LEAST_8_CHARACTERS_AND_A_CAPITAL_LETTER = "La contraseña debe tener al menos 8 caracteres y una mayuscula"
    const val USER_OR_PASSWORD_ARE_INCORRECT = "El usuario y/o contraseña son incorrectos"
    const val GAME_NOT_FOUND_ERROR_MESSAGE = "No se encontró un juego con el ID: %s"
    const val GAME_NOT_FOUND_ERROR_MESSAGE_ALIAS = "No se encontró un juego con el ALIAS: %s"
    const val COMMENT_NOT_FOUND_ERROR_MESSAGE = "No se encontró el comentario con el ID: %s"
    const val REQUEST_TO_BE_DEVELOPER_NOT_FOUND_ERROR_MESSAGE = "No se encontró una peticion para ser desarrollador con el ID: %s"
    const val INVALID_COMMENT_RATING = "El rating debe estar entre 1 y 5"
    const val INVALID_COMMENT_CONTENT = "El comentario puede tener hasta 250 caracteres"
    const val YOU_ARE_NOT_AUTHORIZED_TO_DO_THIS_ACTION_DUE_TO_YOUR_ROLE = "No puedes realizar esta acción debido a que no eres Admin de la plataforma."
    const val NOT_EXISTS_BANNER_WITH_ID = "No existe un banner con Id: %s"
    const val YOU_MUST_INSERT_A_TITLE_FOR_THE_BANNER = "Debes ingresar un título para el banner"
    const val YOU_MUST_INSERT_A_CONTENT = "Debes ingresar un contenido"
    const val YOU_MUST_INSERT_A_PICTURE_FOR_THE_BANNER = "Debes ingresar una imágen para el banner"
}