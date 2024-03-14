package softwaredesign.hw2.features.menu.data.request

class UpdateDishInMenuRequestData(
    var itemId: Long?,
    var name: String?,
    var price: Double?,
    var preparationTime: Int?,
    var maxAmountPerOrder: Int?
)