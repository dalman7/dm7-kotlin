package kr.co.dm7.blackpink.webflux.handler

import kr.co.dm7.blackpink.domain.mongo.Domain
import kr.co.dm7.blackpink.repository.mongo.DomainRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

/**
 * webflux 로 handler 구현 샘플
 * dm7 프로젝트의 DomainController 부분을 webflux 기반으로 구현했다
 *
 * @author doubleseven
 * @version 1.0
 */
@Component
class DomainHandler(
        val domainRepository: DomainRepository) {

    /**
     * Domain 전체 정보를 얻는다.
     */
    fun getAll(request: ServerRequest) = ok()
            .body(
                    domainRepository.findAll(),
                    Domain::class.java
            )

    /**
     * Domain 정보를 추가한다.
     */
    fun addAll(request: ServerRequest): Mono<ServerResponse> =
            request.bodyToMono(Domain::class.java)
                    .flatMap {
                        domainRepository.save(it).toMono()
                    }
                    .flatMap {
                        ok().body(it.toMono(), Domain::class.java)
                    }
                    .onErrorResume {
                        badRequest().body(
                                ErrorResponse(message = it.message ?: "error").toMono(), ErrorResponse::class.java)
                    }

    /**
     * Domain 정보 한건을 얻는다
     */
    fun getOneById(request: ServerRequest) = ok()
            .body(
                    domainRepository.findById(request.pathVariable("id")),
                    Domain::class.java
            )

    /**
     * 이름으로 도메인 객체 찾기
     */
    fun getOneByName(request: ServerRequest) = ok()
            .body(
                    domainRepository.findFirstByName(request.pathVariable("name")),
                    Domain::class.java
            )

    /**
     * 이름으로 도메인 객체 찾기 ( mongo json query 이용 )
     */
    fun getOneByCustomName(request: ServerRequest) = ok()
            .body(
                    domainRepository.findCustomByName(request.pathVariable("name")),
                    Domain::class.java
            )

    /**
     * 이름과 노출여부 정보로 도메인 객체 찾기
     */
    fun getOneByNameAndDisplay(request: ServerRequest) = ok()
            .body(
                    domainRepository.findByNameAndDisplay(request.pathVariable("name"), request.pathVariable("display").toBoolean()),
                    Domain::class.java
            )
    /**
     * 도메인의 상태 계속 호출할때마다 바꿔준다. ( display :true <-> false )
     */
    fun changeDisplayAndGet(request: ServerRequest): Mono<ServerResponse> {
        val name = request.pathVariable("name")
        domainRepository.findFirstByName(name)
                .subscribe(
                        { value -> domainRepository.updateDisplay(value.name, !value.display) },
                        { error -> error.printStackTrace() },
                        { println("completed without a value") }
                )
        return ok()
                .body(
                        domainRepository.findFirstByName(name),
                        Domain::class.java
                )
    }

    fun getByRegExpName(request: ServerRequest) =
            ok().body(
                    domainRepository.findCustomByRegExDomain(request.pathVariable("name")),
                    Domain::class.java
            )

    data class ErrorResponse(val message: String)
}