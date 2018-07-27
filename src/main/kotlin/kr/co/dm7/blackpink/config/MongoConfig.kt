package kr.co.dm7.blackpink.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.mapping.MongoMappingContext
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

/**
 * MongoDB 사용을 위한 설정 정보다
 *
 * @author doubleseven
 * @version 1.0
 */
@Configuration
@EnableMongoRepositories(basePackages = arrayOf("kr.co.dm7.blackpink.repository.mongo"))
class MongoConfig {

    /***
     * 몽고DB 의 템플릿을 Bean 으로 선언한다
     * 현재 소스상으로는 몽고템플릿이 없더라도 정상적으로 동작을 한다
     * 추후 필요할 수 있으므로 일단 전체적인 로직 체크시에는 특별히 본 선언은 확인/파악할 필요는 없을 듯 하다
     *
     * @param mongoDbFactory
     * @param context
     * @return
     */
    @Bean
    fun mongoTemplate(mongoDbFactory: MongoDbFactory, context: MongoMappingContext): MongoTemplate {
        val converter = MappingMongoConverter(DefaultDbRefResolver(mongoDbFactory), context)
        converter.setTypeMapper(DefaultMongoTypeMapper(null))
        return MongoTemplate(mongoDbFactory, converter)
    }

    /***
     * 몽고DB 를 위한 EventListener 로 몽고DB 데이터가 POJO 에 mapping 될때 정보들을 확인해 볼 수 있다
     * 기본으로 찍히는 로그는 o.s.d.m.c.m.event.LoggingEventListener 로 시작하며
     * ( onBeforeConvert, onBeforeSave, onAfterSave onAfterLoad onAfterConvert ..... ) 등의 메소드 정보 출력으로
     * 데이터 정보를 확인할 수 있다
     * 상세한 내용은 @org.springframework.data.mongodb.core.mapping.event.LoggingEventListener 의 정보로 확인 가능하다
     * 범용적인 Logger 를 사용해도 크게 무방하여 필요할까도 생각되지만 MongoDB 데이터 흐름에 대한 체크 용도로는 나쁘지 않을 것 같다
     * 다만 해당 로그를 별도의 로그 파일로 분리를 해야 하는 경우에는 어떻게 해야 하는지는 다시 확인을 해 봐야 할 것 같다
     * @return
     */
    @Bean
    fun mongoEventListener() = LoggingEventListener()
}
