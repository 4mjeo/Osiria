package com.example.ohsiria.domain.auth.service

import com.example.ohsiria.domain.auth.presentation.dto.ReissueRequest
import com.example.ohsiria.domain.auth.presentation.dto.TokenResponse
import com.example.ohsiria.global.config.jwt.TokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReissueService(
    private val tokenProvider: TokenProvider
) {
    @Transactional(readOnly = true)
    fun execute(request: ReissueRequest): TokenResponse =
        tokenProvider.reissue(request.refreshToken)
}
