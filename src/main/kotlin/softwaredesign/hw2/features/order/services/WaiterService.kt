package softwaredesign.hw2.features.order.services

import org.springframework.stereotype.Service
import softwaredesign.hw2.features.menu.db.enitites.MenuItem
import softwaredesign.hw2.features.menu.db.repository.MenuItemRepository
import softwaredesign.hw2.features.order.db.entities.Order
import softwaredesign.hw2.features.order.db.entities.OrderItem
import softwaredesign.hw2.features.order.db.enums.OrderStatus
import softwaredesign.hw2.features.order.db.repository.OrderRepository
import softwaredesign.hw2.features.order.exceptions.OrderException

@Service
class WaiterService(
    private val kitchenService: KitchenService,
    private val orderRepository: OrderRepository,
    private val menuItemRepository: MenuItemRepository
) {

    fun acceptOrder(orderItems: List<OrderItem>?, userId: Long?) {
        if (userId == null) {
            throw OrderException("Войдите в систему, чтобы сделать заказ")
        }
        if (orderItems == null) {
            throw OrderException("Заказ не может быть пустым")
        }
        var totalCost = 0.0
        var totalTime = 0
        for (item in orderItems) {
            val dish = menuItemRepository.findByName(item.dishName)
                ?: throw OrderException("В меню нет позиции с названием \"${item.dishName}\"")
            if (dish.maxAmountPerOrder < 1) {
                throw OrderException("К сожалению, на кухне не хватает продуктов, чтобы приготовить  ${dish.maxAmountPerOrder} ${dish.name} за один заказ")
            }
            totalCost += dish.price * item.amount
            totalTime += dish.preparationTime * item.amount
        }
        val order = Order(
            userId = userId,
            items = orderItems.map { it -> menuItemRepository.findByName(it.dishName)!! }.toMutableList(),
            status = OrderStatus.ACCEPTED
        )
        orderRepository.save(order)
        kitchenService.acceptOrder(order)
    }

    fun cancelLastOrder(userId: Long?) {
        if (userId == null) {
            throw OrderException("Войдите в систему, чтобы отменить заказ")
        }
        val orders = orderRepository.findByUserId(userId)
        if (orders.isEmpty()) {
            throw OrderException("Вы не делали заказов")
        }
        kitchenService.cancelOrder(orders[0].id)
    }

    fun cancelOrderById(userId: Long?, orderId: Long?) {
        if (userId == null) {
            throw OrderException("Войдите в систему, чтобы отменить заказ")
        }
        val order = orderRepository.findById(orderId) ?: throw OrderException("Такого заказа не поступало")
        if (order.userId != userId) {
            throw OrderException("Вы не делали этого заказа и не можете его отменить")
        }
        kitchenService.cancelOrder(order.id)
    }

    fun payLastOrder(userId: Long?) {
        if (userId == null) {
            throw OrderException("Войдите в систему, чтобы оплатить заказ")
        }
        val orders = orderRepository.findByUserId(userId)
        if (orders.isEmpty()) {
            throw OrderException("Вы не делали заказов")
        }
        val order = orders[0]
        if (order.status == OrderStatus.COMPLETED || order.status == OrderStatus.CANCELED) {
            throw OrderException("Заказ уже завершён и оплачен либо отменён")
        }
        order.status = OrderStatus.COMPLETED
        orderRepository.save(order)
    }

    fun payOrderById(userId: Long?, orderId: Long?) {
        if (userId == null) {
            throw OrderException("Войдите в систему, чтобы оплатить заказ")
        }
        val order = orderRepository.findById(orderId) ?: throw OrderException("Такого заказа не поступало")
        if (order.userId != userId) {
            throw OrderException("Вы не делали этот заказ и не можете его оплатить")
        }
        order.status = OrderStatus.COMPLETED
        orderRepository.save(order)
    }

    fun addDishToOrder(orderId: Long?, menuItemId: Long?, userId: Long?) {
        if (userId == null) {
            throw OrderException("Войдите в систему, чтобы оплатить заказ")
        }
        var order: Order? = orderRepository.findById(orderId) ?: throw OrderException("Заказа с таким id не поступало")
        var dish: MenuItem? =
            menuItemRepository.findById(menuItemId) ?: throw OrderException("В меню нет позиции с таким id")
        order!!.items!!.add(dish!!)
        orderRepository.save(order)
    }

    fun getOrderStatus(orderId: Long?): String {
        val order: Order = orderRepository.findById(orderId) ?: throw OrderException("Заказа с таким id не поступало")
        return when (order.status) {
            OrderStatus.ACCEPTED -> "Accepted"
            OrderStatus.READY -> "Ready"
            OrderStatus.IN_PROGRESS -> "In progress"
            OrderStatus.COMPLETED -> "Completed"
            OrderStatus.CANCELED -> "Canceled"
            else -> ""
        }
    }
}