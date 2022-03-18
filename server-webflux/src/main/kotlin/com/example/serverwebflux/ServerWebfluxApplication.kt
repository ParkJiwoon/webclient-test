package com.example.serverwebflux

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ServerWebfluxApplication

fun main(args: Array<String>) {
    // 쓰레드 1개만 사용
    System.setProperty("reactor.netty.ioWorkerCount", "1")
    runApplication<ServerWebfluxApplication>(*args)
}

