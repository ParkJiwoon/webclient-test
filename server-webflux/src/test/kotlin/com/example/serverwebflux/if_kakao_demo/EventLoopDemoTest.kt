package com.example.serverwebflux.if_kakao_demo

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.Duration

@WebFluxTest(controllers = [EventLoopDemoController::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventLoopDemoTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @BeforeAll
    fun setUp() {
        System.setProperty("reactor.netty.ioWorkerCount", "4")
        webTestClient = webTestClient.mutate()
            .responseTimeout(Duration.ofMillis(30000))
            .build()
    }

    @Test
    fun testSleep() {
        this.webTestClient.get().uri("http://localhost:8080/sleep")
            .exchange()
            .expectStatus().isOk
    }

    @RepeatedTest(10)
    fun testOk() {
        this.webTestClient.get().uri("http://localhost:8080/ok")
            .exchange()
            .expectStatus().isOk
    }
}
