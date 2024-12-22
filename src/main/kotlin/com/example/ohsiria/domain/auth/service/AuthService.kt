package com.example.ohsiria.domain.auth.service

import com.example.ohsiria.domain.auth.presentation.dto.LoginRequest
import com.example.ohsiria.domain.auth.presentation.dto.ReissueRequest
import com.example.ohsiria.domain.auth.presentation.dto.TokenResponse

interface AuthService {

    fun login(request: LoginRequest): TokenResponse

    fun reissue(request: ReissueRequest): TokenResponse
}
