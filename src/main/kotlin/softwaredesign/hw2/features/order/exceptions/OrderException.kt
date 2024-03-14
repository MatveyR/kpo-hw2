package softwaredesign.hw2.features.order.exceptions

class OrderException : Exception {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}