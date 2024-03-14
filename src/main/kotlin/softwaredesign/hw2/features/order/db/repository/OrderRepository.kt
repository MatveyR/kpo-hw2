package softwaredesign.hw2.features.order.db.repository

import org.springframework.data.jpa.repository.JpaRepository
import softwaredesign.hw2.features.order.db.entities.Order

interface OrderRepository : JpaRepository<Order, Long> {
    fun findById(id: Long?): Order?
    fun findByUserId(usedId: Long?): MutableList<Order>
}