package com.example.ohsiria.global.config.security

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebSecurityConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "http://localhost:3000"
            )
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "HEAD")
            .allowedHeaders("*")
    }
}
