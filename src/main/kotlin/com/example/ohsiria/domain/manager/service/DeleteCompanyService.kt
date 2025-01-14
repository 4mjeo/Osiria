package com.example.ohsiria.domain.manager.service

import com.example.ohsiria.domain.company.exception.CompanyNotFoundException
import com.example.ohsiria.domain.company.repository.CompanyRepository
import com.example.ohsiria.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class DeleteCompanyService(
    private val companyRepository: CompanyRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun execute(companyId: UUID) {
        val company = companyRepository.findByIdOrNull(companyId) ?: throw CompanyNotFoundException

        companyRepository.delete(company)
        userRepository.delete(company.user)
    }
}
