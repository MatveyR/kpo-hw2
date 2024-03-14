package softwaredesign.hw2.features.user.dto

import softwaredesign.hw2.features.user.db.enums.UserRole

data class UserDto(
    val username: String?,
    val password: String?,
    val role: UserRole?
)