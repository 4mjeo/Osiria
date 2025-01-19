package com.example.ohsiria.domain.reservation.presentation.dto.request

import org.jetbrains.annotations.NotNull
import java.time.LocalDate
import java.util.*

data class ReserveRequest(
    @field:NotNull
    val startDate: LocalDate,

    @field:NotNull
    val endDate: LocalDate,

    @field:NotNull
    val phoneNumber: String,

    @field:NotNull
    val name: String,

    @field:NotNull
    val accountNumber: String,

    @field:NotNull
    val roomId: UUID
)
