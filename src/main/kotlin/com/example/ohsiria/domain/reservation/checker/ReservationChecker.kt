package com.example.ohsiria.domain.reservation.checker

import com.example.ohsiria.domain.company.entity.Company
import com.example.ohsiria.domain.company.exception.CompanyMismatchException
import com.example.ohsiria.domain.reservation.entity.Reservation
import com.example.ohsiria.domain.reservation.entity.ReservationStatus
import com.example.ohsiria.domain.reservation.exception.AlreadyCanceledException
import com.example.ohsiria.domain.reservation.exception.InvalidCancellationException
import com.example.ohsiria.domain.reservation.exception.InvalidDateRangeException
import com.example.ohsiria.domain.reservation.exception.ReservationConflictException
import com.example.ohsiria.domain.reservation.repository.ReservationRepositoryCustom
import com.example.ohsiria.domain.room.entity.Room
import com.example.ohsiria.global.common.facade.UserFacade
import com.example.ohsiria.global.config.error.exception.PermissionDeniedException
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Service
class ReservationChecker(
    private val userFacade: UserFacade,
    private val reservationRepositoryCustom: ReservationRepositoryCustom,
) {

    fun checkPermission(company: Company) {
        if (userFacade.getCurrentUser().id != company.user.id)
            throw CompanyMismatchException
    }

    fun checkReservationConflict(startDate: LocalDate, endDate: LocalDate, room: Room) {
        if (reservationRepositoryCustom.existsActiveReservationByDateRangeAndRoom(startDate, endDate, room))
            throw ReservationConflictException
    }

    fun checkDateRange(startDate: LocalDate, endDate: LocalDate) {
        val today = LocalDate.now()
        when {
            endDate.isBefore(startDate) ->
                throw InvalidDateRangeException
            ChronoUnit.DAYS.between(startDate, endDate) > 3 ->
                throw InvalidDateRangeException
            ChronoUnit.DAYS.between(today, startDate) < 7 ->
                throw InvalidDateRangeException
        }
    }

    fun isHistory(reservation: Reservation, currentDate: LocalDate): Boolean {
        return reservation.status == ReservationStatus.CANCELED || reservation.endDate < currentDate
    }

    fun isCurrent(reservation: Reservation, currentDate: LocalDate): Boolean {
        return (reservation.status == ReservationStatus.WAITING || reservation.status == ReservationStatus.RESERVED)
                && reservation.endDate >= currentDate
    }

    fun checkCancellationPermission(reservation: Reservation) {
        if (reservation.company.user.id != userFacade.getCurrentUser().id)
            throw PermissionDeniedException
    }

    fun checkCancellationValidity(reservation: Reservation) {
        if (LocalDate.now().plusDays(3).isAfter(reservation.startDate))
            throw InvalidCancellationException
    }

    fun checkAlreadyCanceled(reservation: Reservation) {
        if (reservation.status == ReservationStatus.CANCELED)
            throw AlreadyCanceledException
    }
}
