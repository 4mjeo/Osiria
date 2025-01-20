package com.example.ohsiria.global.env.sms

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("sms")
class SmsProperty(
    val accessKey: String?,
    val secretKey: String?,
    val serviceId: String?,
    val sender: String?
)
