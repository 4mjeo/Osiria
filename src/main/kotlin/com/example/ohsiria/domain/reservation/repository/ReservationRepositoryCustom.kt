package com.example.ohsiria.domain.reservation.repository

import com.example.ohsiria.domain.company.entity.Company
import java.time.LocalDate

interface ReservationRepositoryCustom {
    fun existsByDateRange(startDate: LocalDate, endDate: LocalDate): Boolean

    fun isHoliday(startDate: LocalDate, endDate: LocalDate): Boolean

    fun countDaysByCompanyAndDateRange(
        company: Company,
        startDate: LocalDate,
        endDate: LocalDate,
        isHoliday: Boolean
    ): Long
}
