package com.example.ohsiria.domain.reservation.service

import com.example.ohsiria.domain.company.repository.CompanyRepository
import com.example.ohsiria.domain.holiday.repository.HolidayRepository
import com.example.ohsiria.domain.reservation.checker.ReservationChecker
import com.example.ohsiria.domain.reservation.entity.ReservationStatus
import com.example.ohsiria.domain.reservation.exception.ReservationNotFoundException
import com.example.ohsiria.domain.reservation.repository.ReservationRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CancelReservationService(
    private val reservationRepository: ReservationRepository,
    private val companyRepository: CompanyRepository,
    private val holidayRepository: HolidayRepository,
    private val reservationChecker: ReservationChecker
) {
    @Transactional
    fun execute(reservationId: UUID) {
        val reservation = reservationRepository.findByIdOrNull(reservationId)
            ?: throw ReservationNotFoundException

        reservationChecker.checkCancellationPermission(reservation)
        reservationChecker.checkCancellationValidity(reservation)
        reservationChecker.checkAlreadyCanceled(reservation)

        val company = reservation.company

        if (reservation.status == ReservationStatus.RESERVED) {
            val dates = company.getDatesWithHolidayInfo(reservation.startDate, reservation.endDate, holidayRepository)
            company.returnRemainDays(dates)
        }

        reservation.cancel()

        companyRepository.save(company)
        reservationRepository.save(reservation)
    }
}
