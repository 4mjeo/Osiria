package com.example.ohsiria.domain.manager.service

import com.example.ohsiria.domain.company.exception.CompanyNotFoundException
import com.example.ohsiria.domain.company.repository.CompanyRepository
import com.example.ohsiria.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteCompanyService(
    private val companyRepository: CompanyRepository,
    private val userRepository: UserRepository,
) {
    @Transactional
    fun execute(accountId: String) {
        val user = userRepository.findByAccountId(accountId) ?: throw CompanyNotFoundException
        val company = companyRepository.findByUser(user) ?: throw CompanyNotFoundException

        companyRepository.delete(company)
        userRepository.delete(user)
    }
}
