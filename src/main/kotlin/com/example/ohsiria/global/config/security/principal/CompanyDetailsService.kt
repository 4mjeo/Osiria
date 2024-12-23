package com.example.ohsiria.global.config.security.principal

import com.example.ohsiria.domain.user.exception.UserNotFoundException
import com.example.ohsiria.domain.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class AuthDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(accountId: String): UserDetails =
        CompanyDetails(userRepository.findByAccountId(accountId) ?: throw UserNotFoundException)
}
