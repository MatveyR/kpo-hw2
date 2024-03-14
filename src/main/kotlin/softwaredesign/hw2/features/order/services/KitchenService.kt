package softwaredesign.hw2.features.order.services

import org.springframework.stereotype.Service
import softwaredesign.hw2.features.order.db.entities.Order
import softwaredesign.hw2.features.order.db.repository.OrderRepository
import java.util.concurrent.Semaphore
import kotlinx.coroutines.*
import softwaredesign.hw2.features.order.db.enums.OrderStatus
import softwaredesign.hw2.features.order.exceptions.OrderException
import java.util.*

@Service
class KitchenService(
    val orderRepository: OrderRepository
) {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val semaphore = Semaphore(3)

    private data class CoroutineContext(val orderId: Long?, val job: Job)

    private val orderQueue: PriorityQueue<Order> = PriorityQueue<Order>(compareBy { it.totalAmount })

    private val coroutineContextMap = HashMap<Long?, CoroutineContext>()

    fun acceptOrder(order: Order) {
        orderQueue.add(order)
        cookProcess()
    }

    fun cookProcess() {
        if (orderQueue.isNotEmpty()) {
            val nextOrder = orderQueue.poll()
            val process = coroutineScope.launch {
                try {
                    semaphore.acquire()
                    nextOrder.status = OrderStatus.IN_PROGRESS
                    orderRepository.save(nextOrder)
                    delay(nextOrder.totalPreparationTime!! * 1000)
                    nextOrder.status = OrderStatus.READY
                    orderRepository.save(nextOrder)
                } catch (e: Exception) {
                    print(e.message)
                } finally {
                    semaphore.release()
                    cookProcess()
                }
            }
            coroutineContextMap[nextOrder.id] = CoroutineContext(nextOrder.id, process)
        }
    }

    fun cancelOrder(orderId: Long?) {
        if (orderId == null) {
            throw OrderException("Укажите заказ, который хотите отменить")
        }
        val orderOpt = orderRepository.findById(orderId)
        if (!orderOpt.isPresent) {
            throw OrderException("Невозможно отменить заказ: заказ не поступал в обработку")
        }
        var order = orderOpt.get()
        if (order.status == OrderStatus.COMPLETED || order.status == OrderStatus.CANCELED) {
            throw OrderException("Заказ уже завершён и оплачен либо отменён")
        }
        val coroutineContext = coroutineContextMap[orderId]
            ?: throw OrderException("Ошибка отмены заказа")
        coroutineContext.job.cancel()
        order.status = OrderStatus.CANCELED
        orderRepository.save(order)
        coroutineContextMap.remove(orderId)
    }
}