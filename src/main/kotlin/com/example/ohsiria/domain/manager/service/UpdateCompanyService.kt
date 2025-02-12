package com.example.ohsiria.domain.manager.service

import com.example.ohsiria.domain.company.exception.AlreadyExistingAccountException
import com.example.ohsiria.domain.company.exception.CompanyNotFoundException
import com.example.ohsiria.domain.company.repository.CompanyRepository
import com.example.ohsiria.domain.manager.presentation.dto.request.UpdateCompanyRequest
import com.example.ohsiria.domain.user.repository.UserRepository
import com.example.ohsiria.global.config.security.AESEncryptionService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UpdateCompanyService(
    private val companyRepository: CompanyRepository,
    private val userRepository: UserRepository,
    private val aesEncryptionService: AESEncryptionService
) {
    @Transactional
    fun execute(companyId: UUID, request: UpdateCompanyRequest) {
        val company = companyRepository.findByIdOrNull(companyId) ?: throw CompanyNotFoundException
        val user = company.user

        if (request.accountId != null && request.accountId != user.accountId) {
            if (userRepository.existsByAccountId(request.accountId)) {
                throw AlreadyExistingAccountException
            }
        }

        val encryptedPassword = request.password?.let { aesEncryptionService.encrypt(it) } ?: user.password

        user.update(
            name = request.name ?: user.name,
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
