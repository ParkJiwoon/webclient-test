package com.example.servermvc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ServerMvcApplication

fun main(args: Array<String>) {
    runApplication<ServerMvcApplication>(*args)
}
