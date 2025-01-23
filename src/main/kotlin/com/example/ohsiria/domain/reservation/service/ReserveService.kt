package com.example.ohsiria.domain.reservation.service

import com.example.ohsiria.domain.company.exception.CompanyNotFoundException
import com.example.ohsiria.domain.company.repository.CompanyRepository
import com.example.ohsiria.domain.holiday.repository.HolidayRepository
import com.example.ohsiria.domain.reservation.checker.ReservationChecker
import com.example.ohsiria.domain.reservation.entity.Reservation
import com.example.ohsiria.domain.reservation.exception.ShortageRemainingDaysException
import com.example.ohsiria.domain.reservation.presentation.dto.request.ReserveRequest
import com.example.ohsiria.domain.reservation.presentation.dto.response.ReserveResponse
import com.example.ohsiria.domain.reservation.repository.ReservationRepository
import com.example.ohsiria.domain.room.exception.RoomNotFoundException
import com.example.ohsiria.domain.room.repository.RoomRepository
import com.example.ohsiria.global.common.facade.UserFacade
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReserveService(
    private val reservationRepository: ReservationRepository,
    private val userFacade: UserFacade,
    private val reservationChecker: ReservationChecker,
    private val companyRepository: CompanyRepository,
    private val holidayRepository: HolidayRepository,
    private val roomRepository: RoomRepository,
) {
    @Transactional
    fun execute(request: ReserveRequest): ReserveResponse {
        val user = userFacade.getCurrentUser()
        val company = companyRepository.findByUser(user) ?: throw CompanyNotFoundException
        val room = roomRepository.findByIdOrNull(request.roomId) ?: throw RoomNotFoundException

        reservationChecker.checkPermission(company)
        reservationChecker.checkDateRange(request.startDate, request.endDate)
        reservationChecker.checkReservationConflict(request.startDate, request.endDate, room)

        val dates = company.getDatesWithHolidayInfo(request.startDate, request.endDate, holidayRepository)

        if (!company.hasEnoughRemainingDays(dates)) {
            throw ShortageRemainingDaysException
        }

        val reservation = Reservation(
            startDate = request.startDate,
            endDate = request.endDate,
            phoneNumber = request.phoneNumber,
            name = request.name,
            accountNumber = request.accountNumber,
            company = company,
            room = room,
        )

        if (request.isPaid) {
            reservation.markAsPaid()
        }

        reservationRepository.save(reservation)

        return ReserveResponse(reservationId = reservation.id!!)
    }
}
