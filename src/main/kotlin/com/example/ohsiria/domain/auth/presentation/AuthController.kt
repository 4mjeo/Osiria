package com.example.ohsiria.domain.auth.presentation

import com.example.ohsiria.domain.auth.presentation.dto.LoginRequest
import com.example.ohsiria.domain.auth.presentation.dto.ReissueRequest
import com.example.ohsiria.domain.auth.presentation.dto.TokenResponse
import com.example.ohsiria.domain.auth.service.LoginService
import com.example.ohsiria.domain.auth.service.ReissueService
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
@Validated
class AuthController(
    private val loginService: LoginService,
    private val reissueService: ReissueService,
) {
    @PostMapping("/login")
    fun login(@RequestBody @Valid request: LoginRequest): TokenResponse
        = loginService.execute(request)

    @PatchMapping("/reissue")
    fun reissue(@RequestBody @Valid request: ReissueRequest): TokenResponse
        = reissueService.execute(request)
}
