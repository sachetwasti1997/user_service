package com.sachet.user_service.config

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.io.ClassPathResource
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class ResourceWebConfig{

    @Bean
    fun staticResourceRouter(): RouterFunction<ServerResponse>{
        return RouterFunctions.resources("/images/get/*", ClassPathResource("uploads/"))
    }

}