package com.example.serverwebflux

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono

@Configuration
class RouterConfig {
    val log: Logger = LoggerFactory.getLogger(RouterConfig::class.java)

    @Bean
    fun route(handler: RouterHandler) = router {
        GET("/call/{id}", handler::call)

        before { request ->
            log.info("Before Filter ${request.pathVariable("id")}")
            request
        }

        after { request, response ->
            log.info("After Filter ${request.pathVariable("id")}")
            response
        }
    }
}

@Controller
class RouterHandler {
    val log: Logger = LoggerFactory.getLogger(RouterHandler::class.java)
    val webClient = WebClient.create("http://localhost:8181")

    fun call(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id")
        log.info("block request $id")

        return webClient.get()
            .uri("/block/$id")
            .retrieve()
            .bodyToMono(String::class.java)
            .flatMap {
                ServerResponse.ok().json().body(
                    Mono.just("[request $id] response $it")
                )
            }
    }
}
