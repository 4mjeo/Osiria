package com.example.ohsiria.global.common.facade

import com.example.ohsiria.domain.user.entity.User
import com.example.ohsiria.domain.user.repository.UserRepository
import com.example.ohsiria.global.config.error.exception.InvalidTokenException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userRepository: UserRepository
) {
    fun getCurrentUser(): User = userRepository.findByAccountId(SecurityContextHolder.getContext().authentication.name)
        ?: throw InvalidTokenException
}