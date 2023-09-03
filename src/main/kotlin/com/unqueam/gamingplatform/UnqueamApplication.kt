package com.unqueam.gamingplatform

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class UnqueamApplication

fun main(args: Array<String>) {
    runApplication<UnqueamApplication>(*args)
}
