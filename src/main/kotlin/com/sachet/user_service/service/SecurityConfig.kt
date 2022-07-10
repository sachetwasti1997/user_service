package com.sachet.user_service.service

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.stereotype.Service

@Service
class SecurityConfig(
    private val authenticationManager: AuthenticationManager,
    private val authenticationConvertor: AuthenticationConvertor
) {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun springSecurityWebFilterChain(serverHttpSecurity: ServerHttpSecurity): SecurityWebFilterChain{
        return serverHttpSecurity
            .authorizeExchange()
            .pathMatchers(HttpMethod.POST, "/v1/notes/user/save").permitAll()
            .pathMatchers(HttpMethod.POST, "/v1/notes/user/login").permitAll()
            .pathMatchers(HttpMethod.GET, "/v1/notes/user/{userId}").hasRole("USER")
            .pathMatchers(HttpMethod.GET, "/v1/notes/user/me").hasRole("USER")
            .pathMatchers(HttpMethod.GET, "/note/user").hasRole("USER")
            .pathMatchers(HttpMethod.POST, "/note/save").hasRole("USER")
            .pathMatchers(HttpMethod.GET, "/note/single/{noteId}").hasRole("USER")
            .pathMatchers(HttpMethod.DELETE, "/note/{noteId}").hasRole("USER")
            .and()
            .httpBasic().disable()
            .formLogin().disable()
            .csrf().disable()
            .authenticationManager(authenticationManager)
            .securityContextRepository(authenticationConvertor)
            .build()
    }

}