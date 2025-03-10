package com.example.ohsiria.domain.manager.presentation.dto.request

import org.jetbrains.annotations.NotNull

data class CreateCompanyAccountRequest(
    @field:NotNull
    val companyName: String,

    @field:NotNull
    val accountId: String,

    @field:NotNull
    val password: String
)
