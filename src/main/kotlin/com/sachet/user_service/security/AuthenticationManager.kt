package com.sachet.user_service.security

import com.sachet.user_service.repository.UserRepository
import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuthenticationManager(
    private val jsonWebTokenUtility: JsonWebTokenUtility,
    private val repository: UserRepository
): ReactiveAuthenticationManager {
    suspend fun createAuthentication(authentication: Authentication?): Authentication?{
        val token = authentication?.credentials as String? ?: ""
        val userName = jsonWebTokenUtility.extractUsername(token)
        val user = repository.findUserByUserName(userName) ?: throw Exception("User Not Found")
        return if (jsonWebTokenUtility.validateToken(token, user)) authentication else null
    }

    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        try {
            return if (authentication != null) mono { createAuthentication(authentication) } else Mono.empty()
        }catch (ex: Exception){
            println(ex)
            throw Exception()
        }
    }
}