package kr.co.dm7.blackpink.webflux.handler

import kr.co.dm7.blackpink.domain.mongo.Domain
import kr.co.dm7.blackpink.repository.mongo.DomainRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@Component
class DomainHandler(
        val domainRepository: DomainRepository) {

    fun getAll(request: ServerRequest) = ServerResponse
            .ok()
            .body(
                    domainRepository.findAll(),
                    Domain::class.java
            )

    fun addAll(request: ServerRequest): Mono<ServerResponse> =
            request.bodyToMono(Domain::class.java)
                    .flatMap {
                        domainRepository.save(it).toMono()
                    }
                    .flatMap {
                        ServerResponse.ok().body(it.toMono(), Domain::class.java)
                    }
                    .onErrorResume {
                        ServerResponse.badRequest().body(
                                ErrorResponse(message = it.message ?: "error").toMono(), ErrorResponse::class.java)
                    }


    fun getOneById(request: ServerRequest) =
            ServerResponse
                    .ok()
                    .body(
                            domainRepository.findById(request.pathVariable("id")),
                            Domain::class.java
                    )

    data class ErrorResponse(val message: String)
}