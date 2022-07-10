package com.sachet.user_service.errors

sealed class Errors {
    class UserNameAlreadyTaken(message: String): RuntimeException(message)
    class UserNotFound(message: String): RuntimeException(message)
}