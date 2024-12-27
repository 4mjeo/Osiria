package com.example.ohsiria.global.config.security.principal

import com.example.ohsiria.domain.user.entity.UserType
import com.example.ohsiria.domain.user.exception.UserNotFoundException
import com.example.ohsiria.domain.user.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class ManagerDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(accountId: String): UserDetails {
        val manager = userRepository.findByAccountId(accountId)
            ?: throw UserNotFoundException

        return ManagerDetails(
            id = manager.id!!,
            accountId = manager.accountId,
            password = manager.password,
            authorities = listOf(SimpleGrantedAuthority(UserType.MANAGER.name))
        )
    }
}
