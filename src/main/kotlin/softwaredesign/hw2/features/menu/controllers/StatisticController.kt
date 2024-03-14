package softwaredesign.hw2.features.menu.controllers

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import softwaredesign.hw2.features.menu.data.response.StatisticResponseData
import softwaredesign.hw2.features.menu.services.StatisticService

@RestController
@RequestMapping("/statistic")
class StatisticController(
    val statisticService: StatisticService
) {

    @Operation(summary = "Получить глобальную статистику ресторана: среднюю оценку заказов, общую выручку и количество заказов")
    @GetMapping("/get-global-statistic")
    fun getGlobalStatistic(): ResponseEntity<StatisticResponseData> {
        return try {
            ResponseEntity.ok().body(statisticService.getGlobalStatistic())
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(StatisticResponseData())
        }
    }

    @Operation(summary = "Получить статистику по конкретному пользователю: среднюю оценку заказов, общую сумму заказов и количество заказов")
    @GetMapping("/get-user-statistic/{userId}")
    fun getUserStatistic(@PathVariable userId: Long?): ResponseEntity<StatisticResponseData> {
        return try {
            ResponseEntity.ok().body(statisticService.getUserStatistic(userId))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(StatisticResponseData())
        }
    }
}