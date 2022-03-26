package com.example.serverwebflux.if_kakao_demo

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@RestController
class EventLoopDemoController {
    val log: Logger = LoggerFactory.getLogger(EventLoopDemoController::class.java)

    @GetMapping("/sleep")
    fun sleep(): Mono<String> {
        log.info("call sleep")
        return Mono.fromSupplier { blockingFunction(10_000L) }
//            .subscribeOn(Schedulers.boundedElastic())
    }

    private fun blockingFunction(sleepMs: Long): String {
        log.info("call blockingFunction")
        Thread.sleep(sleepMs)
        return "OK"
    }

    @GetMapping("/ok")
    fun health(): String {
        log.info("call ok")
        return "OK"
    }
}
