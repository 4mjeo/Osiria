package com.example.ohsiria.domain.reservation.service

import com.example.ohsiria.domain.company.repository.CompanyRepository
import com.example.ohsiria.domain.holiday.repository.HolidayRepository
import com.example.ohsiria.domain.reservation.exception.InvalidCancellationException
import com.example.ohsiria.domain.reservation.exception.ReservationNotFoundException
import com.example.ohsiria.domain.reservation.repository.ReservationRepository
import com.example.ohsiria.global.common.facade.UserFacade
import com.example.ohsiria.global.config.error.exception.PermissionDeniedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.UUID

@Service
class CancelReservationService(
    private val userFacade: UserFacade,
    private val reservationRepository: ReservationRepository,
    private val companyRepository: CompanyRepository,
    private val holidayRepository: HolidayRepository,
) {
    @Transactional
    fun execute(reservationId: UUID) {
        val user = userFacade.getCurrentUser()
        val reservation = reservationRepository.findByIdOrNull(reservationId)
            ?: throw ReservationNotFoundException

        if (reservation.company.user.id != user.id)
            throw PermissionDeniedException

        if (LocalDate.now().plusDays(3).isAfter(reservation.startDate))
            throw InvalidCancellationException

        val company = reservation.company
        val dates = reservation.startDate.datesUntil(reservation.endDate.plusDays(1))
            .map { it to (it.dayOfWeek in listOf(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)) }
            .toList()

        val holidays = holidayRepository.findByDateBetween(reservation.startDate, reservation.endDate)
            .map { it.date }
            .toSet()

        company.returnRemainingDays(dates, holidays)

        reservation.cancel()
        company.updateRemainingDays(dates)

        companyRepository.save(company)
        reservationRepository.save(reservation)
    }
}
