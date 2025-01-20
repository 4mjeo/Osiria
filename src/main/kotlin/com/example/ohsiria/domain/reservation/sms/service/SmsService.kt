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
) {
    fun sendSms(messageDto: MessageDTO): SmsResponseDTO {
        val urlPath = "/sms/v2/services/${smsProperty.serviceId}/messages"
        val fullUrl = "https://sens.apigw.ntruss.com$urlPath"
        val timestamp = System.currentTimeMillis().toString()
        val signature = makeSignature("POST", urlPath, timestamp, smsProperty.accessKey, smsProperty.secretKey)

        val headers = HttpHeaders().apply {
            set("Content-Type", "application/json; charset=utf-8")
            set("x-ncp-iam-access-key", smsProperty.accessKey)
            set("x-ncp-apigw-timestamp", timestamp)
            set("x-ncp-apigw-signature-v2", signature)
        }

        val body = SmsRequestDTO(
            type = "LMS",
            contentType = "COMM",
            countryCode = "82",
            from = smsProperty.sender,
            content = messageDto.content,
            messages = listOf(messageDto)
        )

        val entity = HttpEntity(body, headers)

        return try {
            val response: ResponseEntity<SmsResponseDTO> =
                restTemplate.exchange(URI(fullUrl), HttpMethod.POST, entity, SmsResponseDTO::class.java)
            response.body ?: throw RuntimeException("Response body is null")
        } catch (e: Exception) {
            throw RuntimeException("Failed to send SMS: ${e.message}", e)
        }
    }

    private fun makeSignature(
        method: String,
        url: String,
        timestamp: String,
        accessKey: String,
        secretKey: String
    ): String {
        val space = " "
        val newLine = "\n"

        val message = StringBuilder()
            .append(method)
            .append(space)
            .append(url)
            .append(newLine)
            .append(timestamp)
            .append(newLine)
            .append(accessKey)
            .toString()

        val signingKey = SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), "HmacSHA256")
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(signingKey)

        val rawHmac = mac.doFinal(message.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(rawHmac)
    }
}
