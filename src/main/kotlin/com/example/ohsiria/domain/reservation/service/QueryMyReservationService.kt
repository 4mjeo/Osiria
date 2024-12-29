package com.example.ohsiria.domain.reservation.service

import com.example.ohsiria.domain.company.exception.CompanyNotFoundException
import com.example.ohsiria.domain.company.repository.CompanyRepository
import com.example.ohsiria.domain.reservation.presentation.dto.response.ReservationResponse
import com.example.ohsiria.domain.reservation.repository.ReservationRepository
import com.example.ohsiria.global.common.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryMyReservationService(
    private val userFacade: UserFacade,
    private val companyRepository: CompanyRepository,
    private val reservationRepository: ReservationRepository,
) {
    @Transactional(readOnly = true)
    fun execute(): List<ReservationResponse> {
        val user = userFacade.getCurrentUser()
        val company = companyRepository.findByUser(user) ?: throw CompanyNotFoundException

        return reservationRepository.findByCompany(company)
            .map { ReservationResponse.from(it) }
    }
}
