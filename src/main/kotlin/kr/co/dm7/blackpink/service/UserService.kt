package kr.co.dm7.blackpink.service

import kr.co.dm7.blackpink.domain.User
import org.springframework.stereotype.Service
import kr.co.dm7.blackpink.repository.map.UserMapRepository;

/**
 * User 서비스 정보다.
 *
 */
interface UserService {

    val users: List<User>

    fun getUser(id: String): User?

    fun setUser(user: User)

    fun removeUser(id: String): User?
}

@Service
class UserServiceImpl(val userMapRepository: UserMapRepository) : UserService {

    override val users: List<User> get() = userMapRepository.all

    override fun getUser(id: String): User? = userMapRepository.getOne(id)

    override fun setUser(user: User) = userMapRepository.setOne(user)

    override fun removeUser(id: String): User? = userMapRepository.remove(id)
}