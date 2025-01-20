package com.example.ohsiria.domain.reservation.sms.service

import com.example.ohsiria.domain.reservation.sms.dto.MessageDTO
import com.example.ohsiria.domain.reservation.sms.dto.SmsRequestDTO
import com.example.ohsiria.domain.reservation.sms.dto.SmsResponseDTO
import com.example.ohsiria.global.env.sms.SmsProperty
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Service
class SmsService(
    private val restTemplate: RestTemplate,
    private val smsProperty: SmsProperty,
){
    fun sendSms(messageDto: MessageDTO): SmsResponseDTO {
        val url = "https://sens.apigw.ntruss.com/sms/v2/services/${smsProperty.serviceId}/messages"
        val timestamp = System.currentTimeMillis().toString()
        val signature = generateSignature(smsProperty.secretKey!!, url, timestamp)

        val headers = HttpHeaders().apply {
            set("X-NCP-APIGW-API-KEY-ID", smsProperty.accessKey)
            set("X-NCP-APIGW-API-KEY", smsProperty.secretKey)
            set("X-NCP-APIGW-API-SIGNATURE", signature)
            set("Content-Type", "application/json")
            set("X-NCP-APIGW-TIMESTAMP", timestamp)
        }

        val body = SmsRequestDTO(
            type = "SMS",
            contentType = "COMM",
            countryCode = "82",
            from = smsProperty.sender!!,
            content = messageDto.content,
            messages = listOf(messageDto)
        )

        val entity = HttpEntity(body, headers)

        return try {
            val response: ResponseEntity<SmsResponseDTO> =
                restTemplate.exchange(URI(url), HttpMethod.POST, entity, SmsResponseDTO::class.java)
            response.body ?: throw RuntimeException("Response body is null")
        } catch (e: Exception) {
            throw RuntimeException("Failed to send SMS: ${e.message}", e)
        }
    }

    private fun generateSignature(secretKey: String, url: String, timestamp: String): String {
        val message = "POST\n$url\n$timestamp"
        val hmacSha256 = Mac.getInstance("HmacSHA256")
        val secretKeySpec = SecretKeySpec(secretKey.toByteArray(), "HmacSHA256")
        hmacSha256.init(secretKeySpec)
        val hash = hmacSha256.doFinal(message.toByteArray())
        return Base64.getEncoder().encodeToString(hash)
    }
}
