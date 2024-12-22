package com.example.ohsiria.global.env.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class TokenProperty(
    val secretKey: String,
    val accessExp: Long,
    val refreshExp: Long
)
