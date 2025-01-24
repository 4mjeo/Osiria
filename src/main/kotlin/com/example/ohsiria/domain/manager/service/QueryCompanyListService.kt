package com.example.ohsiria.domain.manager.service

import com.example.ohsiria.domain.company.repository.CompanyRepository
import com.example.ohsiria.domain.manager.presentation.dto.response.CompanyListResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryCompanyListService(
    private val companyRepository: CompanyRepository,
) {
    @Transactional(readOnly = true)
    fun execute(): List<CompanyListResponse> {
        return companyRepository.findAll().map {
            CompanyListResponse(
                it.user.name,
                it.user.accountId,
                it.user.password
            )
        }
    }
}
