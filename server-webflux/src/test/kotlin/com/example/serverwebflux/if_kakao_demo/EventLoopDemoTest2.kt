package com.example.serverwebflux.if_kakao_demo

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.Duration

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventLoopDemoTest2 {

    private val log: Logger = LoggerFactory.getLogger(EventLoopDemoTest2::class.java)
    private lateinit var webTestClient: WebTestClient

    @BeforeAll
    fun setUp() {
        System.setProperty("reactor.netty.ioWorkerCount", "4")

        webTestClient = WebTestClient.bindToServer()
            .baseUrl("http://localhost:8080")
            .responseTimeout(Duration.ofMillis(30000))
            .build()
    }

    @RepeatedTest(10)
    fun testOk() {
        this.webTestClient.get().uri("/ok")
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun testSleep() {
        this.webTestClient.get().uri("/sleep")
            .exchange()
            .expectStatus().isOk
    }

}
