package com.example.serverwebflux

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ServerWebfluxApplication

fun main(args: Array<String>) {
    System.setProperty("reactor.netty.ioWorkerCount", "1")
    runApplication<ServerWebfluxApplication>(*args)
}

