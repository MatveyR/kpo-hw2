package softwaredesign.hw2.features.user.services

import org.mindrot.jbcrypt.BCrypt
import softwaredesign.hw2.features.user.exceptions.AuthException
import org.springframework.stereotype.Service
import softwaredesign.hw2.global.data.UserData
import softwaredesign.hw2.features.user.db.entities.User
import softwaredesign.hw2.features.user.db.enums.UserRole
import softwaredesign.hw2.features.user.db.repository.UserRepository

@Service
class AuthService(private val userRepository: UserRepository) {

    fun setUserData(user: User?) {
        if (user != null) {
            UserData.userId = user.id
            UserData.isLoggedIn = true
            UserData.username = user.username
            UserData.isAdmin = (user.role == UserRole.ADMIN)
        } else {
            UserData.userId = null
            UserData.isLoggedIn = false
            UserData.username = null
            UserData.isAdmin = false
        }
    }

    fun signUp(username: String?, password: String?, isAdming: Boolean? = false): Long? {
        if (username == null || password == null) {
            throw AuthException("Необходимо ввести и логин, и пароль")
        }
        if (userRepository.findByUsername(username) != null) {
            throw AuthException("Пользователь с таким логином уже существует")
        }
        val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())

        return userRepository.save(User(username = username, password = hashedPassword)).id
    }

    fun logIn(username: String?, password: String?): Boolean? {
        if (username == null || password == null) {
            throw AuthException("Необходимо ввести и логин, и пароль")
        }
        var user: User? = userRepository.findByUsername(username)
            ?: throw AuthException("Пользователя с данным логином не существует")
        if (!BCrypt.checkpw(password, user!!.password)) {
            throw AuthException("Неверный пароль")
        }
        setUserData(user)
        return true
    }

    fun logOut() {
        if (!UserData.isLoggedIn) {
            throw AuthException("Вы не входили в аккаунт")
        }
        setUserData(null)
    }

    fun appointAdmin(username: String?) {
        if (!UserData.isLoggedIn) {
            throw AuthException("Войдите в аккаунт, чтобы воспользоваться функцией")
        }
        if (!UserData.isAdmin) {
            throw AuthException("Войдите под аккаунтом админа, чтобы воспользоваться функцией")
        }
        if (username == null) {
            throw AuthException("Введите имя пользователя чтобы назначить его админом")
        }
        var user = userRepository.findByUsername(username)
        if (user == null) {
            throw AuthException("Пользователя с таким именем не существует")
        }
        if (user.role == UserRole.ADMIN) {
            throw AuthException("Пользователь уже является админом")
        }
        user.role = UserRole.ADMIN
        userRepository.save(user)
    }
}