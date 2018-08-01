package kr.co.dm7.blackpink.webflux.routing

import kr.co.dm7.blackpink.webflux.handler.DomainHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class RoutingConfig {

    @Bean
    fun domainRouter(domainHandler: DomainHandler) = router {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/domains", domainHandler::getAll)
            "/domain".nest {
                GET("{id}", domainHandler::getOneById)
                PUT("/", domainHandler::addAll)
            }
        }
    }
}