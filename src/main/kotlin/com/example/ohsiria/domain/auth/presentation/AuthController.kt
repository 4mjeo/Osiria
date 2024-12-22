package com.example.ohsiria.domain.auth.presentation

import com.example.ohsiria.domain.auth.service.AuthService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
@Validated
class AuthController(
    private val authService: AuthService
)