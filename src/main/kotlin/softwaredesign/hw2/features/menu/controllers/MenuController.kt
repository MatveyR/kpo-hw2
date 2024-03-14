package softwaredesign.hw2.features.menu.controllers

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.Operation
import jdk.jfr.Description
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import softwaredesign.hw2.features.menu.data.request.AddDishToMenuRequestData
import softwaredesign.hw2.features.menu.data.request.UpdateDishInMenuRequestData
import softwaredesign.hw2.features.menu.services.MenuService

@RestController
@RequestMapping("/menu")
class MenuController(
    var menuService: MenuService
) {

    @Operation(
        description = "Добавить блюдо в меню. Необходимо указать параметры блюда: название, стоимость, время приготовления и максимальное количество в одном заказе",
        summary = "Добавить блюдо в меню"
    )
    @PostMapping("/add-dish-to-menu")
    fun addDishToMenu(@RequestBody addDishToMenuRequestData: AddDishToMenuRequestData): ResponseEntity<String> {
        return try {
            menuService.addDishToMenu(
                addDishToMenuRequestData.name,
                addDishToMenuRequestData.price,
                addDishToMenuRequestData.prepataionTime,
                addDishToMenuRequestData.maxAmountPerOrder
            )
            ResponseEntity.ok().body("Блюдо добавлено в меню")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @Operation(
        description = "Изменить блюдо в меню. Можно указать следующие параметры блюда: название, стоимость, время приготовления и максимальное количество в одном заказе",
        summary = "Изменить блюдо в меню"
    )
    @PostMapping("/update-dish-in-menu")
    fun updateDishInMenu(@RequestBody updateDishInMenuRequestData: UpdateDishInMenuRequestData): ResponseEntity<String> {
        return try {
            menuService.updateDishInMenu(
                updateDishInMenuRequestData.itemId,
                updateDishInMenuRequestData.name,
                updateDishInMenuRequestData.price,
                updateDishInMenuRequestData.preparationTime,
                updateDishInMenuRequestData.maxAmountPerOrder
            )
            ResponseEntity.ok().body("Блюдо обновлено")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @Operation(summary = "Удаление блюда из меню")
    @DeleteMapping("/delete-dish-from-menu/{itemId}")
    fun deleteDishFromMenu(@PathVariable itemId: Long?): ResponseEntity<String> {
        return try {
            menuService.deleteDishFromMenu(itemId)
            ResponseEntity.ok().body("Блюдо удалено")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}