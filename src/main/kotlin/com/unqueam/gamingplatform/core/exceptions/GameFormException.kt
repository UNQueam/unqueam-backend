package com.unqueam.gamingplatform.core.exceptions

class GameFormException (val errorsMap: Map<String, List<String>>) : RuntimeException()