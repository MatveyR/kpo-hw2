package softwaredesign.hw2.features.menu.services

import org.springframework.stereotype.Service
import softwaredesign.hw2.features.menu.db.enitites.MenuItem
import softwaredesign.hw2.features.menu.db.repository.MenuItemRepository
import softwaredesign.hw2.features.menu.exceptions.MenuException
import softwaredesign.hw2.global.data.UserData

@Service
class MenuService(private val menuItemRepository: MenuItemRepository) {

    fun addDishToMenu(name: String?, price: Double?, preparationTime: Int?, maxAmountPerOrder: Int?) {
        if (!UserData.isLoggedIn) {
            throw MenuException("Войдите в аккаунт чтобы использовать эту функцию")
        }
        if (!UserData.isAdmin) {
            throw MenuException("Вы должны быть админом чтобы использовать эту функцию")
        }
        if (name == null || price == null || preparationTime == null || maxAmountPerOrder == null) {
            throw MenuException("Чтобы добавить блюдо укажите все его параметры")
        }
        val menuItem = MenuItem(
            name = name,
            price = price,
            preparationTime = preparationTime,
            maxAmountPerOrder = maxAmountPerOrder
        )
        menuItemRepository.save(menuItem)
    }

    fun updateDishInMenu(itemId: Long?, name: String?, price: Double?, preparationTime: Int?, maxAmountPerOrder: Int?) {
        if (!UserData.isLoggedIn) {
            throw MenuException("Войдите в аккаунт чтобы использовать эту функцию")
        }
        if (!UserData.isAdmin) {
            throw MenuException("Вы должны быть админом чтобы использовать эту функцию")
        }
        if (itemId == null) {
            throw MenuException("Укажите id изменяемого блюда")
        }
        if (!menuItemRepository.findById(itemId).isPresent) {
            throw MenuException("Блюда с таким id нет в меню")
        }
        val menuItem = menuItemRepository.findById(itemId).get()
        if (name == null && price == null && preparationTime == null && maxAmountPerOrder == null) {
            throw MenuException("Укажите хотя бы один параметр, который хотите изменить")
        }
        if (name != null) {
            menuItem.name = name
        }
        if (price != null) {
            menuItem.price = price
        }
        if (preparationTime != null) {
            menuItem.preparationTime = preparationTime
        }
        if (maxAmountPerOrder != null) {
            menuItem.maxAmountPerOrder = maxAmountPerOrder
        }
        menuItemRepository.save(menuItem)
    }

    fun deleteDishFromMenu(itemId: Long?) {
        if (!UserData.isLoggedIn) {
            throw MenuException("Войдите в аккаунт чтобы использовать эту функцию")
        }
        if (!UserData.isAdmin) {
            throw MenuException("Вы должны быть админом чтобы использовать эту функцию")
        }
        if (itemId == null) {
            throw MenuException("Укажите id изменяемого блюда")
        }
        if (!menuItemRepository.findById(itemId).isPresent) {
            throw MenuException("Блюда с таким id нет в меню")
        }
        val menuItem = menuItemRepository.findById(itemId).get()
        menuItemRepository.delete(menuItem)
    }
}