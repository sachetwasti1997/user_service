package com.sachet.user_service.errors

import com.sachet.user_service.model.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ErrorHandler {

    @ExceptionHandler(Errors.UserNameAlreadyTaken::class)
    fun handleUserNameAlreadyPresentEx(ex: Errors.UserNameAlreadyTaken) = ResponseEntity.ok().body(ApiResponse(ex.message, 400))

    @ExceptionHandler(Errors.UserNotFound::class)
    fun handleUserNotFound(ex: Errors.UserNotFound) = ResponseEntity.ok().body(ApiResponse(ex.message, 404))

}