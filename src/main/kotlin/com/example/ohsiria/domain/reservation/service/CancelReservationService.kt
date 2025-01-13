package com.example.ohsiria.domain.reservation.service

import com.example.ohsiria.domain.company.repository.CompanyRepository
import com.example.ohsiria.domain.holiday.repository.HolidayRepository
import com.example.ohsiria.domain.reservation.entity.ReservationStatus
import com.example.ohsiria.domain.reservation.exception.AlreadyCanceledException
import com.example.ohsiria.domain.reservation.exception.InvalidCancellationException
import com.example.ohsiria.domain.reservation.exception.ReservationNotFoundException
import com.example.ohsiria.domain.reservation.repository.ReservationRepository
import com.example.ohsiria.global.common.facade.UserFacade
import com.example.ohsiria.global.config.error.exception.PermissionDeniedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

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

        if (reservation.status == ReservationStatus.CANCELED)
            throw AlreadyCanceledException

        val company = reservation.company

        if (reservation.status == ReservationStatus.RESERVED) {
            val dates = company.getDatesWithHolidayInfo(reservation.startDate, reservation.endDate, holidayRepository)
            company.returnRemainingDays(dates)
        }

        reservation.cancel()

        companyRepository.save(company)
        reservationRepository.save(reservation)
    }
}
