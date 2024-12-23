package com.example.ohsiria.domain.manager.service

import com.example.ohsiria.domain.company.entity.Company
import com.example.ohsiria.domain.company.exception.AlreadyExistingAccountException
import com.example.ohsiria.domain.company.repository.CompanyRepository
import com.example.ohsiria.domain.manager.presentation.dto.CreateCompanyAccountRequest
import com.example.ohsiria.domain.user.entity.User
import com.example.ohsiria.domain.user.entity.UserType
import com.example.ohsiria.domain.user.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateCompanyAccountService(
    private val companyRepository: CompanyRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {

    @Transactional
    fun execute(request: CreateCompanyAccountRequest) {
        if (userRepository.existsByAccountId(request.accountId)) throw AlreadyExistingAccountException

        val user = User(
            name = request.name,
            accountId = request.accountId,
            password = passwordEncoder.encode(request.password),
            type = UserType.COMPANY
        )
        userRepository.save(user)

        val company = Company(user = user)
        companyRepository.save(company)
    }
}
