package kr.co.dm7.blackpink.webflux.routing

import kr.co.dm7.blackpink.webflux.handler.DomainHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

/**
 * webflux 로 route 구현 샘플
 * dm7 프로젝트의 Controller 부분을 webflux 기반으로 구현했다
 *
 * @author doubleseven
 * @version 1.0
 */
@Configuration
class RoutingConfig {

    @Bean
    fun domainRouter(domainHandler: DomainHandler) = router {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/domains", domainHandler::getAll)                                  // [GET] http://localhost:8080/domains
            "/domain".nest {
                GET("/{id}", domainHandler::getOneById)                             // [GET] http://localhost:8080/domain/{id}
                PUT("/", domainHandler::addAll)                                     // [GET] http://localhost:8080/domain    request body json : {"name": "칠칠월드2","url": "http://okay.com","address": {"postCode": "777-777","firstAddress": "경기도 성남시","secondAddress": "오케이 아파트 가동 111호"}, "display": true}
                "/name".nest {
                    GET("/{name}", domainHandler::getOneByName)                     // [GET] http://localhost:8080/domain/name/만동홈페이지
                    "/regexp".nest {
                        GET("/{name}", domainHandler::getByRegExpName)              // [GET] http://localhost:8080/domain/name/regexp/만동
                    }
                    "/{name}".nest {
                        GET("/changeDisplay", domainHandler::changeDisplayAndGet)   // [GET] http://localhost:8080/domain/name/만동홈페이지/changeDisplay
                        GET("/{display}", domainHandler::getOneByNameAndDisplay)    // [GET] http://localhost:8080/domain/name/만동홈페이지/false or false
                    }
                }
                "/name2".nest {
                    GET("/{name}", domainHandler::getOneByCustomName)                // [GET] http://localhost:8080/domain/name2/만동홈페이지
                }
            }
        }
    }
}