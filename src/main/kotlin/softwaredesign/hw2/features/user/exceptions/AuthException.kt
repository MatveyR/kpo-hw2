package softwaredesign.hw2.features.user.exceptions

class AuthException : Exception {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}