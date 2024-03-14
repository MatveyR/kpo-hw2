package softwaredesign.hw2.features.menu.exceptions

class StatisticException : Exception {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}