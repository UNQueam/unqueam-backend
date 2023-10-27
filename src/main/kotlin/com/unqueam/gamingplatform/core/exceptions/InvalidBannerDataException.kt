package com.unqueam.gamingplatform.core.exceptions

class InvalidBannerDataException(val errorsMap: Map<String, List<String>>) : RuntimeException()