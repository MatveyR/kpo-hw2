package softwaredesign.hw2.features.review.db.repository

import org.springframework.data.jpa.repository.JpaRepository
import softwaredesign.hw2.features.review.db.entities.Review

interface ReviewRepository : JpaRepository<Review, Long> {
    fun findByUserId(userId: Long?): List<Review>?
    fun findByOrderId(orderId: Long?): List<Review>?
    fun findById(id: Long?): Review?
}