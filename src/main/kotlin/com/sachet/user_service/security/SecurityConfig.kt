package com.sachet.user_service.security

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers
import org.springframework.stereotype.Service
import org.springframework.web.reactive.config.EnableWebFlux

@EnableWebFluxSecurity
class SecurityConfig(
    private val authenticationManager: AuthenticationManager,
    private val authenticationConvertor: AuthenticationConvertor
) {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun springSecurityWebFilterChain(serverHttpSecurity: ServerHttpSecurity): SecurityWebFilterChain{
        return serverHttpSecurity
            .securityMatcher (
                NegatedServerWebExchangeMatcher(
                    ServerWebExchangeMatchers.pathMatchers("/uploads/*"))
            )
            .authorizeExchange()
            .pathMatchers(HttpMethod.POST, "/v1/user/signUp").permitAll()
            .pathMatchers(HttpMethod.POST, "/v1/user/login").permitAll()
            .pathMatchers(HttpMethod.PUT, "/images/upload/*").hasRole("USER")
            .pathMatchers(HttpMethod.GET, "/images/get/*").permitAll()
            .pathMatchers(HttpMethod.GET, "/v1/user/get").hasRole("USER")
            .and()
            .httpBasic().disable()
            .formLogin().disable()
            .csrf().disable()
            .authenticationManager(authenticationManager)
            .securityContextRepository(authenticationConvertor)
            .build()
    }

}