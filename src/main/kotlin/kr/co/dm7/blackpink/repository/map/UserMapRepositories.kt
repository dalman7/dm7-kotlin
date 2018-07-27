package kr.co.dm7.blackpink.repository.map

import kr.co.dm7.blackpink.domain.User
import org.springframework.stereotype.Repository
import java.util.*

interface MapRepository<T, ID> {

    val all: List<T>

    fun getOne(id: ID): T?

    fun setOne(user: T)

    fun remove(id: ID): T?
}

interface UserMapRepository : MapRepository<User, String>

@Repository
class UserMapRepositoryImpl : UserMapRepository {

    internal var userMap: MutableMap<String, User> = HashMap()

    override val all: List<User>
        get() = userMap.values.toList()

    override fun getOne(id: String): User? = userMap[id]

    override fun setOne(user: User) { userMap[user.id] = user}

    override fun remove(id: String): User? = userMap.remove(id)
}