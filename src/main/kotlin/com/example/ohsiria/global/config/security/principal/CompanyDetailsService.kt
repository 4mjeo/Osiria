package com.example.ohsiria.global.config.security.principal

import com.example.ohsiria.domain.user.entity.UserType
import com.example.ohsiria.domain.user.exception.UserNotFoundException
import com.example.ohsiria.domain.user.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CompanyDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(accountId: String): UserDetails {
        val company = userRepository.findByAccountId(accountId)
            ?: throw UserNotFoundException

        return CompanyDetails(
            id = company.id!!,
            accountId = company.accountId,
            password = company.password,
            authorities = listOf(SimpleGrantedAuthority(UserType.COMPANY.name))
        )
    }
}
