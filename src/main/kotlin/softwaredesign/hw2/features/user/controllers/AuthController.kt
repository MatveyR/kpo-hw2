package softwaredesign.hw2.features.user.controllers

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import softwaredesign.hw2.features.user.data.request.LogInRequestData
import softwaredesign.hw2.features.user.data.request.SignUpRequestData
import softwaredesign.hw2.features.user.data.request.AppointAdminRequestData
import softwaredesign.hw2.features.user.services.AuthService

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {

    @Operation(summary = "Зарегистрироваться")
    @PostMapping("/signup")
    fun signUp(@RequestBody signUpRequestData: SignUpRequestData): ResponseEntity<String> {
        return try {
            authService.signUp(signUpRequestData.username, signUpRequestData.password)
            ResponseEntity.ok().body("Регистрация прошла успешно! Вы вошли в аккаунт")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @Operation(summary = "Выйти из аккаунта")
    @GetMapping("/logout")
    fun logOut(): ResponseEntity<String> {
        return try {
            authService.logOut()
            ResponseEntity.ok().body("Вы вышли из аккаунта")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @Operation(summary = "Войти в аккаунт")
    @PostMapping("/login")
    fun logIn(@RequestBody logInRequestData: LogInRequestData): ResponseEntity<String> {
        return try {
            authService.logIn(logInRequestData.username, logInRequestData.password)
            ResponseEntity.ok().body("Вы успешно вошли в аккаунт!")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @Operation(summary = "Назначить пользователя админом")
    @PostMapping("/appoint-admin")
    fun appointAdmin(@RequestBody appointAdminRequestData: AppointAdminRequestData): ResponseEntity<String> {
        return try {
            authService.appointAdmin(appointAdminRequestData.username)
            ResponseEntity.ok().body("Новый админ назначен")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

}