package com.example.ohsiria.domain.auth.presentation.dto

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
