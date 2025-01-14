package com.example.ohsiria.global.config.security

import com.example.ohsiria.global.config.error.handler.ExceptionHandlerFilter
import com.example.ohsiria.global.config.filter.TokenFilter
import com.example.ohsiria.global.config.jwt.JwtTokenResolver
import com.example.ohsiria.global.config.jwt.TokenProvider
import mu.KLogger
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import java.util.*

import com.example.ohsiria.domain.user.entity.UserType.COMPANY
import com.example.ohsiria.domain.user.entity.UserType.MANAGER
import org.springframework.http.HttpMethod

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenProvider: TokenProvider,
    private val exceptionHandlerFilter: ExceptionHandlerFilter,
    private val tokenResolver: JwtTokenResolver,
) {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .cors { it.configurationSource { CorsConfiguration().applyPermitDefaultValues() } }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

        http
            .authorizeHttpRequests { authorize ->
                authorize.requestMatchers("/auth/**").permitAll()
                authorize.requestMatchers("/manager/**").hasAuthority(MANAGER.name)
                authorize.requestMatchers(HttpMethod.POST, "/reservation").hasAuthority(COMPANY.name)
                authorize.requestMatchers(HttpMethod.PATCH, "/reservation/{reservation-id}").hasAuthority(COMPANY.name)
                authorize.requestMatchers(HttpMethod.GET, "/reservation/company").hasAuthority(COMPANY.name)
                authorize.requestMatchers(HttpMethod.GET, "/reservation/manager").hasAuthority(MANAGER.name)
                authorize.requestMatchers(HttpMethod.GET, "/company").hasAuthority(COMPANY.name)
                authorize.requestMatchers(HttpMethod.PATCH, "/reservation/confirm/{reservation-id}").hasAuthority(MANAGER.name)
                authorize.requestMatchers(HttpMethod.POST, "/file/**").hasAuthority(MANAGER.name)

                authorize.anyRequest().authenticated()
            }

        http
            .addFilterBefore(TokenFilter(tokenResolver, tokenProvider), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(exceptionHandlerFilter, TokenFilter::class.java)

        return http.build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun bankEncoder(): Base64.Encoder = Base64.getEncoder()

    @Bean
    fun logger(): KLogger = KotlinLogging.logger {}
}
