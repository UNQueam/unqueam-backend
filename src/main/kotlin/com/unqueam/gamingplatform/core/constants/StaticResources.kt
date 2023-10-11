package com.unqueam.gamingplatform.core.constants

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource

object StaticResources {

    fun unqueamIcon(): Resource = ClassPathResource("static/unqueam-icon.png")
}