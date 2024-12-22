package com.example.ohsiria.domain.auth.entity

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(timeToLive = 60 * 24 * 7)
data class RefreshToken(
    @Id
    var token: String,

    @Indexed
    var accountId: String
)
