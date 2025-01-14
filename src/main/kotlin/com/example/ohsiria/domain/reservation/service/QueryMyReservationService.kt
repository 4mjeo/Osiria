package com.example.ohsiria.domain.reservation.service

import com.example.ohsiria.domain.company.exception.CompanyNotFoundException
import com.example.ohsiria.domain.company.repository.CompanyRepository
import com.example.ohsiria.domain.reservation.checker.ReservationChecker
import com.example.ohsiria.domain.reservation.entity.ReservationStatus
import com.example.ohsiria.domain.reservation.presentation.dto.response.ReservationResponse
import com.example.ohsiria.domain.reservation.repository.ReservationRepository
import com.example.ohsiria.global.common.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class QueryMyReservationService(
    private val userFacade: UserFacade,
    private val companyRepository: CompanyRepository,
    private val reservationRepository: ReservationRepository,
    private val reservationChecker: ReservationChecker
) {
    @Transactional(readOnly = true)
    fun execute(isHistory: Boolean): List<ReservationResponse> {
        val user = userFacade.getCurrentUser()
        val company = companyRepository.findByUser(user) ?: throw CompanyNotFoundException
        val currentDate = LocalDate.now()

        return reservationRepository.findByCompany(company)
            .filter { reservation ->
                if (isHistory) {
                    reservationChecker.isHistory(reservation, currentDate)
                } else {
                    reservationChecker.isCurrent(reservation, currentDate)
                }
            }
            .map { ReservationResponse.from(it) }
    }
}
