package softwaredesign.hw2.features.order.data.request

import softwaredesign.hw2.features.order.db.entities.OrderItem

class AcceptOrderRequestBody(var items: List<OrderItem>?)