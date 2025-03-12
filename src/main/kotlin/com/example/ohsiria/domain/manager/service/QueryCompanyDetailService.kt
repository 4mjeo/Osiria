package com.example.ohsiria.domain.manager.service

import com.example.ohsiria.domain.company.exception.CompanyNotFoundException
import com.example.ohsiria.domain.company.repository.CompanyRepository
import com.example.ohsiria.domain.manager.presentation.dto.response.CompanyDetailResponse
import com.example.ohsiria.domain.reservation.repository.ReservationRepository
import com.example.ohsiria.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryCompanyDetailService(
    private val companyRepository: CompanyRepository,
    private val reservationRepository: ReservationRepository,
    private val userRepository: UserRepository,
) {
    @Transactional(readOnly = true)
    fun execute(accountId: String): CompanyDetailResponse {
        val user = userRepository.findByAccountId(accountId) ?: throw CompanyNotFoundException
        val company = companyRepository.findByUser(user) ?: throw CompanyNotFoundException
        val reservations = reservationRepository.findByCompany(company)

        return CompanyDetailResponse.from(company, reservations)
    }
}
