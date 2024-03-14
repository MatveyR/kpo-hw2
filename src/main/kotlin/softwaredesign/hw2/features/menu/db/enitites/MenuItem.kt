package softwaredesign.hw2.features.menu.db.enitites

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
@Table(name = "menu")
data class MenuItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String? = null,

    var price: Double = 0.0,

    var preparationTime: Int = 0,

    var maxAmountPerOrder: Int = 0
)