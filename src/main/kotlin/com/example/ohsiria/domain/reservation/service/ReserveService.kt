package com.example.ohsiria.domain.reservation.service

import com.example.ohsiria.domain.company.repository.CompanyRepository
import com.example.ohsiria.domain.reservation.checker.ReservationChecker
import com.example.ohsiria.domain.reservation.entity.Reservation
import com.example.ohsiria.domain.reservation.presentation.dto.ReserveRequest
import com.example.ohsiria.domain.reservation.repository.ReservationRepository
import com.example.ohsiria.domain.user.repository.UserRepository
import com.example.ohsiria.global.common.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReserveService(
    private val reservationRepository: ReservationRepository,
    private val userFacade: UserFacade,
    private val reservationChecker: ReservationChecker,
    private val companyRepository: CompanyRepository,
) {
    @Transactional
    fun execute(request: ReserveRequest) {
        val user = userFacade.getCurrentUser()
        val company = companyRepository.findByUser(user)

        reservationChecker.checkPermission(company!!)
        reservationChecker.checkDateRange(request.startDate, request.endDate)
        reservationChecker.checkReservationConflict(request.startDate, request.endDate, request.roomType)
        reservationChecker.checkRemainingDays(company, request.startDate, request.endDate)

        val reservation = Reservation(
            startDate = request.startDate,
            endDate = request.endDate,
            headCount = request.headCount,
            phoneNumber = request.phoneNumber,
            name = request.name,
            company = company,
            roomType = request.roomType,
        )
        reservationRepository.save(reservation)
    }
}
