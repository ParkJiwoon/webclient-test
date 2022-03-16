package com.example.serverwebflux

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.blockhound.BlockHound

@SpringBootApplication
class ServerWebfluxApplication

fun main(args: Array<String>) {
    BlockHound.install()    // Blocking 로직 감지
    System.setProperty("reactor.netty.ioWorkerCount", "1")
    runApplication<ServerWebfluxApplication>(*args)
}

