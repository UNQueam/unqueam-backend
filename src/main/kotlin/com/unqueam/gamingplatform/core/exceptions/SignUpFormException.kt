package com.unqueam.gamingplatform.core.exceptions

class SignUpFormException(val errorsMap: Map<String, List<String>>) : RuntimeException()