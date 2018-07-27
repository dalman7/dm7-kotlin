package kr.co.dm7.blackpink.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import kr.co.dm7.blackpink.domain.Subscriber

@Repository
interface UserRepository : JpaRepository<Subscriber, Long>
