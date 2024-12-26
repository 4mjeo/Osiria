package com.example.ohsiria.domain.reservation.checker

import com.example.ohsiria.domain.company.entity.Company
import com.example.ohsiria.domain.company.exception.CompanyMismatchException
import com.example.ohsiria.domain.company.exception.InvalidDateRangeException
import com.example.ohsiria.domain.reservation.entity.Reservation
import com.example.ohsiria.domain.reservation.exception.ReservationConflictException
import com.example.ohsiria.domain.reservation.repository.ReservationRepository
import com.example.ohsiria.global.common.facade.UserFacade
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ReservationChecker(
    private val userFacade: UserFacade,
    private val reservationRepository: ReservationRepository
) {

    fun checkPermission(company: Company) {
        if (userFacade.getCurrentUser().id != company.id)
            throw CompanyMismatchException
    }

    fun checkPermission(reservation: Reservation) {
        checkPermission(reservation.company)
    }

    fun checkReservationConflict(startDate: LocalDate, endDate: LocalDate) {
        if (reservationRepository.existsByStartDateAndEndDate(startDate, endDate))
            throw ReservationConflictException
    }

    fun checkDateRange(startDate: LocalDate, endDate: LocalDate) {
        if (endDate.isBefore(startDate) || startDate.plusDays(3).isBefore(endDate))
            throw InvalidDateRangeException
    }

    fun checkRemainingDays(company: Company, startDate: LocalDate, endDate: LocalDate) {

    }
}