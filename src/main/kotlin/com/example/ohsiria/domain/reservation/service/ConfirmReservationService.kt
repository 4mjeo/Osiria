package com.example.ohsiria.domain.reservation.service

import com.example.ohsiria.domain.company.repository.CompanyRepository
import com.example.ohsiria.domain.holiday.repository.HolidayRepository
import com.example.ohsiria.domain.reservation.entity.ReservationStatus
import com.example.ohsiria.domain.reservation.exception.ReservationNotFoundException
import com.example.ohsiria.domain.reservation.repository.ReservationRepository
import com.example.ohsiria.domain.user.entity.UserType
import com.example.ohsiria.global.common.facade.UserFacade
import com.example.ohsiria.global.config.error.exception.PermissionDeniedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ConfirmReservationService(
    private val reservationRepository: ReservationRepository,
    private val userFacade: UserFacade,
    private val companyRepository: CompanyRepository,
    private val holidayRepository: HolidayRepository
) {
    @Transactional
    fun execute(reservationId: UUID, newStatus: ReservationStatus) {
        val user = userFacade.getCurrentUser()
        val reservation = reservationRepository.findByIdOrNull(reservationId) ?: throw ReservationNotFoundException

        if (user.type != UserType.MANAGER) {
            throw PermissionDeniedException
        }

        val company = reservation.company

        when (newStatus) {
            ReservationStatus.RESERVED -> {
                reservation.confirm()
                val dates = company.getDatesWithHolidayInfo(reservation.startDate, reservation.endDate, holidayRepository)
                company.updateRemainingDays(dates)
            }
            ReservationStatus.CANCELED -> {
                if (reservation.status == ReservationStatus.RESERVED) {
                    val dates = company.getDatesWithHolidayInfo(reservation.startDate, reservation.endDate, holidayRepository)
                    company.returnRemainingDays(dates)
                }
                reservation.cancel()
            }
            ReservationStatus.WAITING -> throw IllegalArgumentException("대기 상태로 변경할 수 없습니다.")
        }

        reservationRepository.save(reservation)
        companyRepository.save(company)
    }
}
