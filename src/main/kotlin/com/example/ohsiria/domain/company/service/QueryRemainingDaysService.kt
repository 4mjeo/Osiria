package com.example.ohsiria.domain.company.service

import com.example.ohsiria.domain.company.exception.CompanyNotFoundException
import com.example.ohsiria.domain.company.presentation.dto.CompanyResponse
import com.example.ohsiria.domain.company.repository.CompanyRepository
import com.example.ohsiria.global.common.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryRemainingDaysService(
    private val companyRepository: CompanyRepository,
    private val userFacade: UserFacade,
) {
    @Transactional(readOnly = true)
    fun execute(): CompanyResponse {
        val user = userFacade.getCurrentUser()
        val company = companyRepository.findByUser(user) ?: throw CompanyNotFoundException

        return CompanyResponse(
            remainWeekday = company.remainWeekday,
            remainWeekend = company.remainWeekend,
        )
    }
}
