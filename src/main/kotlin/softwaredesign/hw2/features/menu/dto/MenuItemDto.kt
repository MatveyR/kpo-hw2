package softwaredesign.hw2.features.menu.dto

data class MenuItemDto(
    val name: String?,
    val price: Double?,
    val preparationTime: Int? = null
)