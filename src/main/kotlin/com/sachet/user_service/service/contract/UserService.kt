package com.sachet.user_service.service.contract

import com.sachet.user_service.model.LogInRequest
import com.sachet.user_service.model.ApiResponse
import com.sachet.user_service.model.User
import org.springframework.http.codec.multipart.FilePart

interface UserService {
    suspend fun signUp(user: User): ApiResponse
    suspend fun login(logInRequest: LogInRequest): String
}