package com.example.ohsiria.domain.manager.service

import com.example.ohsiria.domain.company.repository.CompanyRepository
import com.example.ohsiria.domain.manager.presentation.dto.response.CompanyListResponse
import com.example.ohsiria.global.config.security.AESEncryptionService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryCompanyListService(
    private val companyRepository: CompanyRepository,
    private val aesEncryptionService: AESEncryptionService,
) {
    @Transactional(readOnly = true)
    fun execute(): List<CompanyListResponse> {
        return companyRepository.findAll().map {
            CompanyListResponse(
                it.user.name,
                it.user.accountId,
                aesEncryptionService.decrypt(it.user.password),
                it.remainWeekend,
                it.remainWeekday
            )
        }
    }
}
