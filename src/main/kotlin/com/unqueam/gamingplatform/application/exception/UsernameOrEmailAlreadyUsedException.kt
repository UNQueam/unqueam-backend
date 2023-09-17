package com.unqueam.gamingplatform.application.exception

import java.lang.RuntimeException

class UsernameOrEmailAlreadyUsedException(message: String) : RuntimeException(message)