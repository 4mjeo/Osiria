package com.example.ohsiria.domain.reservation.repository

import com.example.ohsiria.domain.company.entity.Company
import com.example.ohsiria.domain.reservation.entity.Reservation
import com.example.ohsiria.domain.room.entity.Room
import java.time.LocalDate

interface ReservationRepositoryCustom {
    fun countDaysByCompanyAndDateRangeAndType(company: Company, startDate: LocalDate, endDate: LocalDate): Long

    fun existsActiveReservationByDateRangeAndRoom(startDate: LocalDate, endDate: LocalDate, room: Room): Boolean

    fun findAvailableRooms(startDate: LocalDate, endDate: LocalDate): List<Room>

    fun findByCompanyAndHistoryAndPaymentStatus(company: Company, isHistory: Boolean, isPaid: Boolean?): List<Reservation>
}
