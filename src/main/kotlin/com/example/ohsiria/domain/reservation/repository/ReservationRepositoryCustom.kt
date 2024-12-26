package com.example.ohsiria.domain.reservation.repository

import com.example.ohsiria.domain.company.entity.Company
import com.example.ohsiria.domain.room.entity.RoomType
import java.time.LocalDate

interface ReservationRepositoryCustom {
    fun existsByDateRangeAndRoom(startDate: LocalDate, endDate: LocalDate, roomType: RoomType): Boolean

    fun isHoliday(startDate: LocalDate, endDate: LocalDate): Boolean

    fun countDaysByCompanyAndDateRangeAndType(company: Company, startDate: LocalDate, endDate: LocalDate, isHoliday: Boolean): Long
}
