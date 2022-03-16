package com.example.servermvc

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class ServerMvcApplication

fun main(args: Array<String>) {
    System.setProperty("server.port", "8181")
    runApplication<ServerMvcApplication>(*args)
}

@RestController
class BlockController {
    val log: Logger = LoggerFactory.getLogger(BlockController::class.java)

    @GetMapping("/block/{id}")
    fun block(@PathVariable id: Long): ResponseEntity<String> {
        log.info("request $id start")
        Thread.sleep(5000)
        log.info("request $id end")
        return ResponseEntity.ok().body("response $id")
    }
}
