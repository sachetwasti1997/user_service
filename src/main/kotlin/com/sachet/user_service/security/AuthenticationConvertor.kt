package com.sachet.user_service.security

import com.sachet.user_service.repository.UserRepository
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Service
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Service
class AuthenticationConvertor(
    private val authenticationManager: AuthenticationManager,
    private val jsonWebTokenUtility: JsonWebTokenUtility,
    private val repository: UserRepository
): ServerSecurityContextRepository {

    override fun save(exchange: ServerWebExchange?, context: SecurityContext?): Mono<Void> {
        return Mono.empty()
    }

    suspend fun createSecurityContext(exchange: ServerWebExchange?, token: String): SecurityContext{
        val userName: String = jsonWebTokenUtility.extractUsername(token)
        val user = repository.findUserByUserName(userName)
        val authorities = mutableListOf<SimpleGrantedAuthority>()
        user?.roles?.forEach {
            authorities.add(SimpleGrantedAuthority(it))
        }
        val passAuthToken = UsernamePasswordAuthenticationToken(
            user?.userName,
            token,
            authorities
        )
        return SecurityContextImpl(authenticationManager.authenticate(passAuthToken).awaitSingleOrNull())
    }

    override fun load(exchange: ServerWebExchange?): Mono<SecurityContext> {
        return Mono.justOrEmpty(exchange?.request?.headers?.getFirst(HttpHeaders.AUTHORIZATION))
            .filter {
                it.startsWith("Bearer ")
            }
            .map {
                it.substring(7)
            }
            .flatMap {
                mono { createSecurityContext(exchange, it) }
            }
    }
}