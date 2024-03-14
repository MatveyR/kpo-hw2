package softwaredesign.hw2.features.order.dto

import softwaredesign.hw2.features.menu.db.enitites.MenuItem
import softwaredesign.hw2.features.order.db.enums.OrderStatus

data class OrderDto(
    val status: OrderStatus?,
    val items: MutableList<MenuItem>?,
)