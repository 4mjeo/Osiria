package com.example.ohsiria.domain.auth.service

import com.example.ohsiria.domain.auth.exception.PasswordMismatchedException
import com.example.ohsiria.domain.auth.presentation.dto.LoginRequest
import com.example.ohsiria.domain.auth.presentation.dto.TokenResponse
import com.example.ohsiria.domain.user.exception.UserNotFoundException
import com.example.ohsiria.domain.user.repository.UserRepository
import com.example.ohsiria.global.config.jwt.TokenProvider
import com.example.ohsiria.global.config.security.AESEncryptionService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LoginService(
    private val userRepository: UserRepository,
    private val aesEncryptionService: AESEncryptionService,
    private val tokenProvider: TokenProvider
) {
    @Transactional(readOnly = true)
    fun execute(request: LoginRequest): TokenResponse {
        val user = userRepository.findByAccountId(request.accountId!!)
            ?: throw UserNotFoundException

        if (request.password != aesEncryptionService.decrypt(user.password)) throw PasswordMismatchedException

        return tokenProvider.receiveToken(user.accountId, user.type)
    }
}
