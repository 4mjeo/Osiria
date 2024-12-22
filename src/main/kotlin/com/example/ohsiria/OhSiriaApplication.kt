package com.example.ohsiria

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class OhSiriaApplication

fun main(args: Array<String>) {
    runApplication<OhSiriaApplication>(*args)
}
