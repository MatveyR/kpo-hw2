package softwaredesign.hw2.features.user.db.entities

import lombok.NoArgsConstructor
import softwaredesign.hw2.features.user.db.enums.UserRole
import jakarta.persistence.*

@Entity
@Table(name = "users")
@NoArgsConstructor
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true)
    var username: String? = null,

    var password: String? = null,

    var role: UserRole? = UserRole.GUEST
)