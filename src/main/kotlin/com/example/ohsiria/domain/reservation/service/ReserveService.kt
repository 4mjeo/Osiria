package com.example.ohsiria.domain.reservation.service

import com.example.ohsiria.domain.company.exception.CompanyNotFoundException
import com.example.ohsiria.domain.company.repository.CompanyRepository
import com.example.ohsiria.domain.holiday.repository.HolidayRepository
import com.example.ohsiria.domain.reservation.checker.ReservationChecker
import com.example.ohsiria.domain.reservation.entity.Reservation
import com.example.ohsiria.domain.reservation.exception.ShortageRemainingDaysException
import com.example.ohsiria.domain.reservation.presentation.dto.ReserveRequest
import com.example.ohsiria.domain.reservation.presentation.dto.ReserveResponse
import com.example.ohsiria.domain.reservation.repository.ReservationRepository
import com.example.ohsiria.global.common.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class ReserveService(
    private val reservationRepository: ReservationRepository,
    private val userFacade: UserFacade,
    private val reservationChecker: ReservationChecker,
    private val companyRepository: CompanyRepository,
    private val holidayRepository: HolidayRepository
) {
    @Transactional
    fun execute(request: ReserveRequest): ReserveResponse {
        val user = userFacade.getCurrentUser()
        val company = companyRepository.findByUser(user) ?: throw CompanyNotFoundException

        reservationChecker.checkPermission(company)
        reservationChecker.checkDateRange(request.startDate, request.endDate)
        reservationChecker.checkReservationConflict(request.startDate, request.endDate, request.roomType)

        val dates = getDatesWithHolidayInfo(request.startDate, request.endDate)

        if (!company.hasEnoughRemainingDays(dates)) {
            throw ShortageRemainingDaysException
        }

        val reservation = Reservation(
            startDate = request.startDate,
            endDate = request.endDate,
            headCount = request.headCount,
            phoneNumber = request.phoneNumber,
            name = request.name,
            company = company,
            roomType = request.roomType
        )
        reservationRepository.save(reservation)

        company.updateRemainingDays(dates)
        companyRepository.save(company)

        return ReserveResponse(reservationId = reservation.id!!)
    }

    private fun getDatesWithHolidayInfo(startDate: LocalDate, endDate: LocalDate): List<Pair<LocalDate, Boolean>> {
        val holidays = holidayRepository.findByDateBetween(startDate, endDate)
        return startDate.datesUntil(endDate.plusDays(1))
            .map { it to holidays.any { holiday -> holiday.date == it } }
            .toList()
    }
}
