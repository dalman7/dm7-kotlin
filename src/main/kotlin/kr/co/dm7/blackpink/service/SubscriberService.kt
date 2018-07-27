package kr.co.dm7.blackpink.service

import kr.co.dm7.blackpink.domain.Subscriber
import kr.co.dm7.blackpink.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class SubscriberService(val userRepository: UserRepository) {

    val users: List<Subscriber>
        get() = userRepository.findAll()

    fun createUser(user: Subscriber): Boolean {
        return try {
            userRepository.save(user)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getUser(id: Long) = userRepository.findById(id)

    fun updateUser(id: Long, user: Subscriber) =
            when (userRepository.findById(id).isPresent) {
                true -> {
                    user.id = id
                    userRepository.save(user)
                }
                false -> null
            }

    fun patchUser(id: Long, user: Subscriber): Subscriber? {
        val fetchedUser = userRepository.findById(id)
        return when {
            fetchedUser.isPresent -> {
                fetchedUser.get().name = user.name
                userRepository.save(fetchedUser.get())
            }
            else -> null
        }
    }

    fun deleteUser(id: Long): Boolean =
            when (userRepository.findById(id).isPresent) {
                true -> {
                    userRepository.deleteById(id)
                    true
                }
                false -> false
            }
}