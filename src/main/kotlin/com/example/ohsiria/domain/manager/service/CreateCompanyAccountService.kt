package com.example.ohsiria.domain.manager.service

import com.example.ohsiria.domain.company.entity.Company
import com.example.ohsiria.domain.company.exception.AlreadyExistingAccountException
import com.example.ohsiria.domain.company.repository.CompanyRepository
import com.example.ohsiria.domain.manager.presentation.dto.request.CreateCompanyAccountRequest
import com.example.ohsiria.domain.manager.presentation.dto.response.CreateCompanyResponse
import com.example.ohsiria.domain.user.entity.User
import com.example.ohsiria.domain.user.entity.UserType
import com.example.ohsiria.domain.user.repository.UserRepository
import com.example.ohsiria.global.config.security.AESEncryptionService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateCompanyAccountService(
    private val companyRepository: CompanyRepository,
    private val userRepository: UserRepository,
    private val aesEncryptionService: AESEncryptionService
) {
    @Transactional
    fun execute(request: CreateCompanyAccountRequest): CreateCompanyResponse {
        if (userRepository.existsByAccountId(request.accountId)) throw AlreadyExistingAccountException

        val user = User(
            name = request.companyName,
            accountId = request.accountId,
            password = aesEncryptionService.encrypt(request.password),
            type = UserType.COMPANY
        )
        userRepository.save(user)

        val company = Company(user = user)
        companyRepository.save(company)

        return CreateCompanyResponse(companyId = company.id!!)
    }
}
