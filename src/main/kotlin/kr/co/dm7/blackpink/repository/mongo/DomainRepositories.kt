package kr.co.dm7.blackpink.repository.mongo

import kr.co.dm7.blackpink.domain.mongo.Domain
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.core.query.Query as MongoQuery
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Domain 데이터를 가져오기 위한 Repository
 */
@Repository
interface DomainRepository : ReactiveMongoRepository<Domain, String>, DomainRepositoryCustom {

    fun findFirstByName(name: String): Mono<Domain>

    fun findByNameAndDisplay(name: String, display: Boolean): Mono<Domain>

    fun findByUrlIsStartingWith(url: String): Flux<Domain>

    @Query("{name:'?0'}")
    fun findCustomByName(name: String): Mono<Domain>

    @Query("{name: { \$regex: ?0 } })")
    fun findCustomByRegExDomain(name: String): Flux<Domain>
}

/**
 * DomainRepository 의 Custom Repository Interface
 */
interface DomainRepositoryCustom {
    fun updateDisplay(name: String, display: Boolean): Long
}

/**
 * Custom Repository 에 대해서 구현
 */
class DomainRepositoryImpl(val mongoTemplate: MongoTemplate) : DomainRepositoryCustom {

    override
    fun updateDisplay(name: String, display: Boolean) = mongoTemplate.updateMulti(
            MongoQuery(Criteria.where("name").`is`(name)),
            object : Update() {
                init {
                    set("display", display)
                }
            },
            Domain::class.java).modifiedCount
}