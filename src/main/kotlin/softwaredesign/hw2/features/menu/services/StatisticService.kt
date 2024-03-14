package softwaredesign.hw2.features.menu.services

import org.springframework.stereotype.Service
import softwaredesign.hw2.features.menu.data.response.StatisticResponseData
import softwaredesign.hw2.features.menu.exceptions.StatisticException
import softwaredesign.hw2.features.order.db.repository.OrderRepository
import softwaredesign.hw2.features.review.db.repository.ReviewRepository
import softwaredesign.hw2.features.user.db.repository.UserRepository
import softwaredesign.hw2.global.data.UserData

@Service
class StatisticService(
    val reviewRepository: ReviewRepository,
    val userRepository: UserRepository,
    val orderRepository: OrderRepository
) {

    fun getGlobalStatistic(): StatisticResponseData {
        if (!UserData.isLoggedIn) {
            throw StatisticException("Войдите в аккаунт чтобы использовать функцию")
        }
        if (!UserData.isAdmin) {
            throw StatisticException("Функция доступна только администраторам")
        }
        val reviews = reviewRepository.findAll()
        val orders = orderRepository.findAll()
        var averageReviewScore = 0.0
        if (!reviews.isNullOrEmpty()) {
            for (review in reviews) {
                averageReviewScore += review.score!!
            }
            averageReviewScore /= reviews.size
        }
        var totalIncome = 0.0
        if (!orders.isNullOrEmpty()) {
            for (order in orders) {
                totalIncome += order.totalAmount!!
            }
        }
        return StatisticResponseData(
            averageReviewScore = averageReviewScore,
            totalIncome = totalIncome,
            orderAmount = orders.size
        )
    }

    fun getUserStatistic(userId: Long?): StatisticResponseData {
        if (!UserData.isLoggedIn) {
            throw StatisticException("Войдите в аккаунт чтобы использовать функцию")
        }
        if (!UserData.isAdmin) {
            throw StatisticException("Функция доступна только администраторам")
        }
        if (userId == null) {
            throw StatisticException("Введите id пользователя, о котором хотите получить статистику")
        }
        if (!userRepository.findById(userId).isPresent) {
            throw StatisticException("Пользователя с таким id не существует")
        }
        val reviews = reviewRepository.findByUserId(userId)
        val orders = orderRepository.findByUserId(userId)
        var averageReviewScore = 0.0
        if (!reviews.isNullOrEmpty()) {
            for (review in reviews) {
                averageReviewScore += review.score!!
            }
            averageReviewScore /= reviews.size
        }
        var totalIncome = 0.0
        if (!orders.isNullOrEmpty()) {
            for (order in orders) {
                totalIncome += order.totalAmount!!
            }
        }
        return StatisticResponseData(
            averageReviewScore = averageReviewScore,
            totalIncome = totalIncome,
            orderAmount = orders.size
        )
    }
}