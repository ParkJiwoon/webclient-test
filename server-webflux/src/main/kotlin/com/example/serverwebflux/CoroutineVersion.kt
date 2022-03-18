package com.example.serverwebflux

import kotlinx.coroutines.flow.asFlow
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono

@Configuration
class CoRouterConfig {
    val log: Logger = LoggerFactory.getLogger(CoRouterConfig::class.java)

    @Bean
    fun coRoute(handler: CoRouterHandler) = coRouter {
        "v2".nest {
            GET("/call/{id}", handler::call)
            GET("/rest/{id}", handler::rest)
            GET("/heavy/{id}", handler::heavy)

            before { request ->
                log.info("Before Filter ${request.pathVariable("id")}")
                log.info("$request")
                request
            }

            after { request, response ->
                log.info("After Filter ${request.pathVariable("id")}")
                response
            }
        }
    }
}

@Controller
class CoRouterHandler {
    val log: Logger = LoggerFactory.getLogger(CoRouterHandler::class.java)
    val webClient = WebClient.create("http://localhost:8181")

    suspend fun call(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id")
        val response = webClient.get()
            .uri("/block/$id")
            .retrieve()
            .awaitBody<String>()

        return ServerResponse.ok().json().bodyValueAndAwait(response)
    }

    suspend fun rest(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id")

        val restTemplate = RestTemplate()
        val response = restTemplate.getForObject("http://localhost:8181/block/$id", String::class.java)

        return ServerResponse.ok().json().bodyValueAndAwait(response!!)
    }

    suspend fun heavy(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id")

        (0..500_000_000).forEach {
            if (it % 100_000_000 == 0) {
                log.info("Request [$id] for: $it")
            }
        }

        return ServerResponse.ok().json().bodyValueAndAwait("coroutine heavy response $id")
    }
}
