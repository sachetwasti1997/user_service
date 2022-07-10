package com.sachet.user_service.service.impl

import com.sachet.user_service.errors.Errors
import com.sachet.user_service.model.LogInRequest
import com.sachet.user_service.model.ApiResponse
import com.sachet.user_service.model.User
import com.sachet.user_service.repository.UserRepository
import com.sachet.user_service.security.JsonWebTokenUtility
import com.sachet.user_service.service.contract.UserService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jsonWebTokenUtility: JsonWebTokenUtility
) : UserService {

    override suspend fun signUp(user: User): ApiResponse {
        val existingUser = user.userName?.let { findUserByUserName(it) }
        existingUser?.let {
            throw Errors.UserNameAlreadyTaken("User Name is already taken")
        }
        user.password = user.password?.let{ passwordEncoder.encode(it) }
        val save = userRepository.saveUser(user)
        return if (save != null) ApiResponse(
            "Account Created Successfully, please login to continue", 200) else ApiResponse("Cannot save user", 500)
    }

    suspend fun findUserByUserName(userName: String?): User?{
        return userRepository.findUserByUserName(userName)
    }

    override suspend fun login(logInRequest: LogInRequest):String {
        val user = findUserByUserName(logInRequest.userName) ?: throw Errors.UserNotFound("User Not Found: Invalid Credentials")
        if (passwordEncoder.matches(logInRequest.password, user.password))
            return jsonWebTokenUtility.generateToken(user)
        throw Errors.UserNotFound("User Not Found: Invalid Credentials")
    }
}