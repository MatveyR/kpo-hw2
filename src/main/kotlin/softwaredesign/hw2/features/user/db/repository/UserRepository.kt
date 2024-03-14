package softwaredesign.hw2.features.user.db.repository

import org.springframework.data.jpa.repository.JpaRepository
import softwaredesign.hw2.features.user.db.entities.User

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}