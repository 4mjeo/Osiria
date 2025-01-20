package com.example.ohsiria.domain.reservation.sms.dto

import java.time.LocalDateTime

data class SmsRequestDTO(
    val type: String,
    val contentType: String,
    val countryCode: String,
    val from: String,
    val content: String,
    val messages: List<MessageDTO>
)

data class MessageDTO(
    val to: String,
    val content: String
)

data class SmsResponseDTO(
    val requestId: String,
    val requestTime: LocalDateTime,
    val statusCode: String,
    val statusName: String
)
