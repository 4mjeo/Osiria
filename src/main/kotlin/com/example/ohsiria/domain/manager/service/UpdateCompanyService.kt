package com.example.ohsiria.domain.manager.service

import com.example.ohsiria.domain.company.exception.AlreadyExistingAccountException
import com.example.ohsiria.domain.company.exception.CompanyNotFoundException
import com.example.ohsiria.domain.company.repository.CompanyRepository
import com.example.ohsiria.domain.manager.presentation.dto.request.UpdateCompanyRequest
import com.example.ohsiria.domain.user.repository.UserRepository
import com.example.ohsiria.global.config.security.AESEncryptionService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateCompanyService(
    private val companyRepository: CompanyRepository,
    private val userRepository: UserRepository,
    private val aesEncryptionService: AESEncryptionService
) {
    @Transactional
    fun execute(accountId: String, request: UpdateCompanyRequest) {
        val user = userRepository.findByAccountId(accountId) ?: throw CompanyNotFoundException
        val company = companyRepository.findByUser(user) ?: throw CompanyNotFoundException

        if (request.accountId != null && request.accountId != user.accountId) {
            if (userRepository.existsByAccountId(request.accountId)) {
                throw AlreadyExistingAccountException
            }
        }

        val encryptedPassword = request.password?.let { aesEncryptionService.encrypt(it) } ?: user.password

        user.update(
            companyName = request.name ?: user.name,
            accountId = request.accountId ?: user.accountId,
            password = encryptedPassword,
        )
        company.update(
            remainWeekend = request.remainWeekend ?: company.remainWeekend,
            remainWeekday = request.remainWeekday ?: company.remainWeekday,
        )

        userRepository.save(user)
        companyRepository.save(company)
    }
}
