package com.example.ohsiria.domain.reservation.presentation.dto

import org.jetbrains.annotations.NotNull
import java.time.LocalDate

data class ReserveRequest(
    @field:NotNull
    val startDate: LocalDate,

    @field:NotNull
    val endDate: LocalDate,

    @field:NotNull
    val headCount: Int,

    @field:NotNull
    val phoneNumber: String,

    @field:NotNull
    val name: String,
)
