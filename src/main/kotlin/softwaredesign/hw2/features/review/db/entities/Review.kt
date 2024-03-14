package softwaredesign.hw2.features.review.db.entities

import jakarta.persistence.*
import lombok.NoArgsConstructor

@Entity
@Table(name = "reviews")
@NoArgsConstructor
class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var userId: Long? = null,

    var orderId: Long? = null,

    var score: Double? = null,

    var message: String? = null
)