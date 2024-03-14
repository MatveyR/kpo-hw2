package softwaredesign.hw2.features.review.controllers

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import softwaredesign.hw2.features.review.data.request.DeleteReviewRequestData
import softwaredesign.hw2.features.review.data.request.PostReviewRequestData
import softwaredesign.hw2.features.review.data.request.UpdateReviewRequestData
import softwaredesign.hw2.features.review.services.ReviewService
import softwaredesign.hw2.global.data.UserData

@RestController
@RequestMapping("/review")
class ReviewController(
    private val reviewService: ReviewService
) {

    @Operation(summary = "Оставить отзыв на последний заказ")
    @PostMapping("/post-review-for-last-order")
    fun postReviewForLastOrder(@RequestBody postReviewRequestData: PostReviewRequestData): ResponseEntity<String> {
        return try {
            reviewService.postReviewForLastOrder(
                postReviewRequestData.score,
                postReviewRequestData.message,
                UserData.userId
            )
            ResponseEntity.ok().body("Отзыв успешно опубликован")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @Operation(summary = "Оставить отзыв на заказ по id")
    @PostMapping("/post-review-for-order-by-id")
    fun postReviewForOrderById(@RequestBody postReviewRequestData: PostReviewRequestData): ResponseEntity<String> {
        return try {
            reviewService.postReviewForOrderById(
                postReviewRequestData.score,
                postReviewRequestData.message,
                postReviewRequestData.orderId,
                UserData.userId
            )
            ResponseEntity.ok().body("Отзыв успешно опубликован!")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @Operation(summary = "Удалить отзыв")
    @DeleteMapping("/delete-review")
    fun deleteReview(@RequestBody deleteReviewRequestData: DeleteReviewRequestData): ResponseEntity<String> {
        return try {
            reviewService.deleteReview(deleteReviewRequestData.reviewId, UserData.userId, UserData.isAdmin)
            ResponseEntity.ok().body("Отзыв удалён")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @Operation(summary = "Обновить отзыв")
    @PostMapping("/update-review")
    fun updateReview(@RequestBody updateReviewRequestData: UpdateReviewRequestData): ResponseEntity<String> {
        return try {
            reviewService.updateReview(
                updateReviewRequestData.reviewId,
                updateReviewRequestData.score,
                updateReviewRequestData.message,
                UserData.userId
            )
            ResponseEntity.ok().body("Отзыв обновлён")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}