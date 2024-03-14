package softwaredesign.hw2.features.review.exceptions

class ReviewException : Exception {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}