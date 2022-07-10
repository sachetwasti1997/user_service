package com.sachet.user_service.controller

import com.sachet.user_service.model.LogInRequest
import com.sachet.user_service.model.ApiResponse
import com.sachet.user_service.model.User
import com.sachet.user_service.service.contract.UserService
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
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

    @PutMapping("/upload/{id}")
    suspend fun uploadProfileImage(@PathVariable id: String, @RequestPart("user_image")file: Mono<FilePart>){
        val userImage = file.awaitSingleOrNull()
        userImage?.let {
            userService.uploadImage(it, id)
        }
    }
}