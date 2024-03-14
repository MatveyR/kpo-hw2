package softwaredesign.hw2.features.review.services

import org.springframework.stereotype.Service
import softwaredesign.hw2.features.order.db.repository.OrderRepository
import softwaredesign.hw2.features.review.db.entities.Review
import softwaredesign.hw2.features.review.db.repository.ReviewRepository
import softwaredesign.hw2.features.review.exceptions.ReviewException

@Service
class ReviewService(
    private val orderRepository: OrderRepository,
    private val reviewRepository: ReviewRepository
) {

    fun postReviewForLastOrder(score: Double?, message: String?, userId: Long?) {
        if (userId == null) {
            throw ReviewException("Войдите в аккаунт, чтобы оставить отзыв")
        }
        val order = orderRepository.findByUserId(userId)
        if (order.isEmpty()) {
            throw ReviewException("Вы не делали заказов")
        }
        if (score == null || score < 1 || score > 5) {
            throw ReviewException("Чтобы оставить отзыв вы должны поставить оценку от 1 до 5")
        }
        reviewRepository.save(
            Review(userId = userId, orderId = order[0].id, score = score, message = message)
        )
    }

    fun postReviewForOrderById(score: Double?, message: String?, orderId: Long?, userId: Long?) {
        if (userId == null) {
            throw ReviewException("Войдите в аккаунт, чтобы оставить отзыв")
        }
        val order = orderRepository.findById(orderId) ?: throw ReviewException("Заказа с таким id не поступало")
        if (order.userId != userId) {
            throw ReviewException("Вы не можете оставить отзыв на не свой заказ")
        }
        if (score == null || score < 1 || score > 5) {
            throw ReviewException("Чтобы оставить отзыв вы должны поставить оценку от 1 до 5")
        }
        reviewRepository.save(
            Review(userId = userId, orderId = orderId, score = score, message = message)
        )
    }

    fun deleteReview(reviewId: Long?, userId: Long?, isAdmin: Boolean = false) {
        if (userId == null) {
            throw ReviewException("Войдите в аккаунт, чтобы удалить отзыв")
        }
        val review = reviewRepository.findById(reviewId) ?: throw ReviewException("Отзыва с таким id не существует")
        if (review.userId != userId && !isAdmin) {
            throw ReviewException("Вы не можете удалить отзыв, если не вы его оставили и вы не являетесь админом")
        }
        reviewRepository.delete(review)
    }

    fun updateReview(reviewId: Long?, score: Double?, message: String?, userId: Long?) {
        if (userId == null) {
            throw ReviewException("Войдите в аккаунт, чтобы удалить отзыв")
        }
        val review = reviewRepository.findById(reviewId) ?: throw ReviewException("Отзыва с таким id не существует")
        if (review.userId != userId) {
            throw ReviewException("Вы не можете редактировать отзыв, который оставляли не вы")
        }
        if (score == null || score < 1 || score > 5) {
            throw ReviewException("Чтобы оставить отзыв вы должны поставить оценку от 1 до 5")
        }
        review.score = score
        review.message = message
        reviewRepository.save(review)
    }
}