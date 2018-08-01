package kr.co.dm7.blackpink.domain.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Document(collection = "domain")
class Domain(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: String? = null,
        @Indexed(unique = true)
        val name: String,
        val url: String,
        val address: Address?,
        val display: Boolean = false
)

class Address(val postCode: String, val firstAddress: String, val secondAddress: String)


