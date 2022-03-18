package com.example.serverwebflux

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Controller
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono


@Configuration
class RouterConfig {
    val log: Logger = LoggerFactory.getLogger(RouterConfig::class.java)

    @Bean
    fun route(handler: RouterHandler) = router {
        GET("/call/{id}", handler::call)
        GET("/rest/{id}", handler::rest)
        GET("/heavy/{id}", handler::heavy)

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

    fun rest(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id")
        log.info("block request $id by restTemplate")

        val restTemplate = RestTemplate()
        val response = restTemplate.getForObject("http://localhost:8181/block/$id", String::class.java)

        return ServerResponse.ok().json().body(
            Mono.just(response!!)
        )
    }

    fun heavy(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id")
        log.info("heavy request $id")

        (0..1_000_000_000).forEach {
            if (it % 100_000_000 == 0) {
                log.info("Request [$id] for: $it")
            }
        }

        return ServerResponse.ok().json().body(
            Mono.just("heavy response $id")
        )
    }
}
