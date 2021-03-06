package com.sachet.user_service.controller

import com.sachet.user_service.model.LogInRequest
import com.sachet.user_service.model.ApiResponse
import com.sachet.user_service.model.User
import com.sachet.user_service.service.contract.UserService
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/v1/user")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/signUp")
    suspend fun signUp(@RequestBody @Valid user: Mono<User>): ApiResponse{
        val userToSave = user.awaitSingle()
        return userService.signUp(userToSave)
    }

    @PostMapping("/login")
    suspend fun login(@RequestBody @Valid logInRequest: LogInRequest) = userService.login(logInRequest)
}