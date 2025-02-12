package com.example.ohsiria.domain.manager.presentation.dto.request

data class UpdateCompanyRequest(
    val name: String?,
    val accountId: String?,
    val password: String?,
    val remainWeekend: Int?,
    val remainWeekday: Int?,
)
