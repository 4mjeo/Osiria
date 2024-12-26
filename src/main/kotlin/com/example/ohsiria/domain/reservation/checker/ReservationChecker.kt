package com.example.ohsiria.domain.reservation.checker

import com.example.ohsiria.domain.company.entity.Company
import com.example.ohsiria.domain.company.exception.CompanyMismatchException
import com.example.ohsiria.domain.company.exception.InvalidDateRangeException
import com.example.ohsiria.domain.reservation.entity.Reservation
import com.example.ohsiria.domain.reservation.exception.ReservationConflictException
import com.example.ohsiria.domain.reservation.exception.ShortageRemainingDaysException
import com.example.ohsiria.domain.reservation.repository.ReservationRepository
import com.example.ohsiria.domain.reservation.repository.ReservationRepositoryCustom
import com.example.ohsiria.domain.room.entity.RoomType
import com.example.ohsiria.global.common.facade.UserFacade
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Service
class ReservationChecker(
    private val userFacade: UserFacade,
    private val reservationRepositoryCustom: ReservationRepositoryCustom
) {

    fun checkPermission(company: Company) {
        if (userFacade.getCurrentUser().id != company.user.id)
            throw CompanyMismatchException
    }

    fun checkReservationConflict(startDate: LocalDate, endDate: LocalDate, roomType: RoomType) {
        if (reservationRepositoryCustom.existsByDateRangeAndRoom(startDate, endDate, roomType))
            throw ReservationConflictException
    }

    fun checkDateRange(startDate: LocalDate, endDate: LocalDate) {
        if (endDate.isBefore(startDate) || ChronoUnit.DAYS.between(startDate, endDate) > 3)
            throw InvalidDateRangeException
    }

    fun checkRemainingDays(company: Company, startDate: LocalDate, endDate: LocalDate, isHoliday: Boolean) {
        val daysCount = reservationRepositoryCustom.countDaysByCompanyAndDateRangeAndType(company, startDate, endDate, isHoliday)
        val remainingDays = if (isHoliday) company.remainWeekend else company.remainWeekday
        if (daysCount + ChronoUnit.DAYS.between(startDate, endDate) > remainingDays) {
            throw ShortageRemainingDaysException
        }
    }
}
