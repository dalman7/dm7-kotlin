package kr.co.dm7.blackpink

import kr.co.dm7.blackpink.domain.mongo.Address
import kr.co.dm7.blackpink.domain.mongo.Domain
import kr.co.dm7.blackpink.repository.mongo.DomainRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(2)
class InitMongoDB(val domainRepository: DomainRepository) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {

        val domains = listOf(
                Domain(
                        name = "만동홈페이지",
                        display = true,
                        url = "http://만동만동.com",
                        address = Address(
                                firstAddress = "인천시 뭐구 뭐동",
                                secondAddress = "뭐뭐 아파트 몇동 몇호",
                                postCode = "123-456"
                        )
                ),
                Domain(
                        name = "종다리우리집",
                        display = true,
                        url = "http://종다리.com",
                        address = Address(
                                firstAddress = "경기도 김포시 뭐동",
                                secondAddress = "머시기 아파트 가동 나호",
                                postCode = "789-111"
                        )
                ),
                Domain(
                        name = "칠칠월드",
                        display = true,
                        url = "http://더블세븐.com",
                        address = Address(
                                firstAddress = "경기도 용인시 수지구",
                                secondAddress = "정말로 아파트 AA동 111호",
                                postCode = "998-778"
                        )
                )
        )
        domainRepository.saveAll(domains).subscribe()
    }
}