package softwaredesign.hw2.features.order.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.parameters.RequestBody
import jakarta.servlet.ServletRequest
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import softwaredesign.hw2.features.order.data.request.AcceptOrderRequestBody
import softwaredesign.hw2.features.order.data.request.AddDishToOrderRequestData
import softwaredesign.hw2.features.order.data.request.CancelOrderByIdRequestData
import softwaredesign.hw2.features.order.data.request.PayOrderByIdRequestData
import softwaredesign.hw2.features.order.services.WaiterService
import softwaredesign.hw2.global.data.UserData

@RestController
@RequestMapping("/order")
class OrderController(
    private val waiterService: WaiterService,
    @Qualifier("httpServletRequest") private val request: ServletRequest
) {

    @Operation(summary = "Сделать заказ")
    @PostMapping("/accept-order")
    fun acceptOrder(@RequestBody acceptOrderRequestBody: AcceptOrderRequestBody): ResponseEntity<String> {
        return try {
            waiterService.acceptOrder(acceptOrderRequestBody.items, UserData.userId)
            ResponseEntity.ok().body("Заказ принят")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @Operation(summary = "Отменить последний заказ")
    @GetMapping("/cancel-last-order")
    fun cancelLastOrder(): ResponseEntity<String> {
        return try {
            waiterService.cancelLastOrder(UserData.userId)
            ResponseEntity.ok().body("Последний заказ отменён")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @Operation(summary = "Отменить заказ по его id")
    @PostMapping("/cancel-order-by-id")
    fun cancelLastOrder(@RequestBody cancelOrderByIdRequestData: CancelOrderByIdRequestData): ResponseEntity<String> {
        return try {
            waiterService.cancelOrderById(cancelOrderByIdRequestData.userId, cancelOrderByIdRequestData.orderId)
            ResponseEntity.ok().body("Заказ с id ${cancelOrderByIdRequestData.orderId} отменён")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @Operation(summary = "Оплатить последний заказ")
    @GetMapping("/pay-last-order")
    fun payLastOrder(): ResponseEntity<String> {
        return try {
            waiterService.payLastOrder(UserData.userId)
            ResponseEntity.ok().body("Заказ успешно оплачен")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @Operation(summary = "Оплатить заказ по его id")
    @PostMapping("/pay-order-by-id")
    fun payOrderById(@RequestBody payOrderByIdRequestData: PayOrderByIdRequestData): ResponseEntity<String> {
        return try {
            waiterService.payOrderById(UserData.userId, payOrderByIdRequestData.orderId)
            ResponseEntity.ok().body("Заказ успешно оплачен")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @Operation(summary = "Добавить блюдо в заказ")
    @PostMapping("/add-dish-to-order")
    fun addDishToOrder(@RequestBody addDishToOrderRequestData: AddDishToOrderRequestData): ResponseEntity<String> {
        return try {
            waiterService.addDishToOrder(
                addDishToOrderRequestData.orderId,
                addDishToOrderRequestData.menuItemId,
                UserData.userId
            )
            ResponseEntity.ok().body("Блюдо добавлено в заказ")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @Operation(summary = "Получить статус заказа")
    @PostMapping("/get-order-status/{orderId}")
    fun getOrderStatus(@PathVariable orderId: Long?): ResponseEntity<String> {
        return try {
            ResponseEntity.ok().body(waiterService.getOrderStatus(orderId))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}