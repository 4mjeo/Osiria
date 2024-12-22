package com.example.ohsiria.domain.auth.presentation.dto

import org.jetbrains.annotations.NotNull

data class ReissueRequest(
    @field:NotNull
    val refreshToken: String
)
