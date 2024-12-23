package com.example.ohsiria.domain.manager.presentation.dto

import org.jetbrains.annotations.NotNull

data class CreateCompanyAccountRequest(
    @field:NotNull
    val name: String,

    @field:NotNull
    val accountId: String,

    @field:NotNull
    val password: String
)
