package com.example.ohsiria.global.env.s3

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("cloud.aws.s3")
data class S3Property(
    val bucket: String,
    val accessKey: String,
    val secretKey: String,
    val region: String
)
