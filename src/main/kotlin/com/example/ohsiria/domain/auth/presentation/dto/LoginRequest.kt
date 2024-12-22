package com.example.ohsiria.domain.auth.presentation.dto

import org.jetbrains.annotations.NotNull

data class LoginRequest(
    @field:NotNull
    val accountId: String?,

    @field:NotNull
    val password: String?
)
