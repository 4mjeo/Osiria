package com.example.ohsiria.domain.manager.service

import com.example.ohsiria.domain.company.exception.CompanyNotFoundException
import com.example.ohsiria.domain.company.repository.CompanyRepository
import com.example.ohsiria.domain.manager.presentation.dto.CompanyDetailResponse
import com.example.ohsiria.domain.reservation.repository.ReservationRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class QueryCompanyDetailService(
    private val companyRepository: CompanyRepository,
    private val reservationRepository: ReservationRepository,
) {
    @Transactional(readOnly = true)
    fun execute(companyId: UUID): CompanyDetailResponse {
        val company = companyRepository.findByIdOrNull(companyId) ?: throw CompanyNotFoundException
        val reservations = reservationRepository.findByCompany(company)

        return CompanyDetailResponse.from(company, reservations)
    }
}
