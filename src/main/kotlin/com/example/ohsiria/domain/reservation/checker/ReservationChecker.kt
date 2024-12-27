package com.example.ohsiria.domain.reservation.checker

import com.example.ohsiria.domain.company.entity.Company
import com.example.ohsiria.domain.company.exception.CompanyMismatchException
import com.example.ohsiria.domain.company.exception.InvalidDateRangeException
import com.example.ohsiria.domain.reservation.exception.ReservationConflictException
import com.example.ohsiria.domain.reservation.exception.ShortageRemainingDaysException
import com.example.ohsiria.domain.reservation.repository.ReservationRepositoryCustom
import com.example.ohsiria.domain.room.entity.RoomType
import com.example.ohsiria.global.common.facade.UserFacade
import com.example.ohsiria.infra.holiday.service.GetHolidayService
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Service
class ReservationChecker(
    private val userFacade: UserFacade,
    private val reservationRepositoryCustom: ReservationRepositoryCustom,
    private val getHolidayService: GetHolidayService
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

    fun checkRemainingDays(company: Company, startDate: LocalDate, endDate: LocalDate) {
        var weekdayCount = 0
        var weekendCount = 0

        var currentDate = startDate
        while (!currentDate.isAfter(endDate)) {
            if (currentDate.dayOfWeek in listOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY) || getHolidayService.isHoliday(currentDate)) {
                weekendCount++
            } else {
                weekdayCount++
            }
            currentDate = currentDate.plusDays(1)
        }

        if (weekendCount > company.remainWeekend || weekdayCount > company.remainWeekday) {
            throw ShortageRemainingDaysException
        }
    }
}
