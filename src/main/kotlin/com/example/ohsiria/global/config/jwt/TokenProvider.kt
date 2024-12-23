package com.example.ohsiria.global.config.jwt

import com.example.ohsiria.domain.auth.entity.RefreshToken
import com.example.ohsiria.domain.auth.presentation.dto.TokenResponse
import com.example.ohsiria.domain.auth.repository.RefreshTokenRepository
import com.example.ohsiria.domain.user.entity.UserType
import com.example.ohsiria.global.config.error.exception.ExpiredTokenException
import com.example.ohsiria.global.config.error.exception.InvalidTokenException
import com.example.ohsiria.global.config.security.principal.CompanyDetailsService
import com.example.ohsiria.global.config.security.principal.ManagerDetailsService
import com.example.ohsiria.global.env.jwt.TokenProperty
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class TokenProvider(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val property: TokenProperty,
    private val companyDetailsService: CompanyDetailsService,
    private val managerDetailsService: ManagerDetailsService
) {

    fun receiveToken(accountId: String, userType: UserType): TokenResponse {
        return TokenResponse(
            accessToken = generateAccessToken(accountId, userType),
            accessExpiredAt = getExp(property.accessExp),
            refreshToken = generateRefreshToken(accountId, userType),
            refreshExpiredAt = getExp(property.refreshExp)
        )
    }

    private fun generateAccessToken(accountId: String, userType: UserType): String {
        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, property.secretKey)
            .setSubject(accountId)
            .claim("userType", userType.name)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + property.accessExp * 1000))
            .setHeaderParam(Header.TYPE, "ACCESS")
            .compact()
    }

    private fun generateRefreshToken(accountId: String, userType: UserType): String {
        refreshTokenRepository.findByIdOrNull(accountId)?.let {
            refreshTokenRepository.delete(it)
        }

        val token = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, property.secretKey)
            .setSubject(accountId)
            .claim("userType", userType.name)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + property.refreshExp * 1000))
            .setHeaderParam(Header.TYPE, "REFRESH")
            .compact()

        refreshTokenRepository.save(RefreshToken(token, accountId))

        return token
    }

    private fun getExp(exp: Long): LocalDateTime {
        return LocalDateTime.now().withNano(0).plusSeconds(exp / 1000)
    }

    fun getAuthentication(token: String): Authentication {
        val claims = getClaims(token)

        if (claims.header[Header.TYPE] != "ACCESS") {
            throw InvalidTokenException
        }

        val userDetails = getUserDetails(claims.body)

        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun getClaims(token: String): Jws<Claims> {
        return try {
            Jwts.parser()
                .setSigningKey(property.secretKey)
                .parseClaimsJws(token)
        } catch (e: Exception) {
            when (e) {
                is ExpiredJwtException -> throw ExpiredTokenException
                else -> throw InvalidTokenException
            }
        }
    }

    private fun getUserDetails(body: Claims): UserDetails {
        val accountId = body.subject
        val userType = UserType.valueOf(body["userType", String::class.java])

        return when (userType) {
            UserType.COMPANY -> companyDetailsService.loadUserByUsername(accountId)
            UserType.MANAGER -> managerDetailsService.loadUserByUsername(accountId)
        }
    }

    fun reissue(refreshToken: String): TokenResponse {
        val claims = getClaims(refreshToken)

        if (claims.header[Header.TYPE] != "REFRESH") {
            throw InvalidTokenException
        }

        val accountId = claims.body.subject
        val userType = UserType.valueOf(claims.body["userType", String::class.java])

        return receiveToken(accountId, userType)
    }
}
