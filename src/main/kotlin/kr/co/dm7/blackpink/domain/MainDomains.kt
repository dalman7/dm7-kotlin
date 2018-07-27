package kr.co.dm7.blackpink.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "TB_USER")
data class Subscriber(
        @Id
        var id: Long,

        @Column(name = "Nick", nullable = false, length = 50)
        var nick: String,

        @Column(name = "Name", nullable = false, length = 50)
        var name: String
)

data class User(val id: String, val name: String)