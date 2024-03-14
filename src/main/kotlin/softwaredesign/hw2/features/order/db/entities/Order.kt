package softwaredesign.hw2.features.order.db.entities

import softwaredesign.hw2.features.order.db.enums.OrderStatus
import softwaredesign.hw2.features.user.db.entities.User

import lombok.NoArgsConstructor
import softwaredesign.hw2.features.menu.db.enitites.MenuItem
import jakarta.persistence.*

@Entity
@Table(name = "orders")
@NoArgsConstructor
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id", insertable = false, updatable = false)
    var userId: Long? = null,

    @Column(name = "status")
    var status: OrderStatus? = null,

    @ManyToMany
    @Column(name = "items")
    var items: MutableList<MenuItem>? = null,

    @Column(name = "total_amount")
    var totalAmount: Double? = null,

    @Column(name = "total_preparation_time")
    var totalPreparationTime: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User? = null
)