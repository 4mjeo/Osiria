package com.example.ohsiria.domain.manager.presentation

import com.example.ohsiria.domain.manager.presentation.dto.CreateCompanyAccountRequest
import com.example.ohsiria.domain.manager.service.CreateCompanyAccountService
import mu.KotlinLogging
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

//@RestController
//@RequestMapping("/manager")
//class ManagerController(
//    private val createCompanyAccountService: CreateCompanyAccountService
//) {
//    @PostMapping
//    fun createCompanyAccount(@RequestBody request: CreateCompanyAccountRequest) {
//        createCompanyAccountService.execute(request)
//    }
//}

@RestController
@RequestMapping("/manager")
class ManagerController(
    private val createCompanyAccountService: CreateCompanyAccountService
) {
    private val log = KotlinLogging.logger {}

    @PostMapping
    fun createCompanyAccount(@RequestBody request: CreateCompanyAccountRequest) {
        log.info { "Received request to create company account: ${request.accountId}" }
        val authentication = SecurityContextHolder.getContext().authentication
        log.info { "Current user: ${authentication.name}, authorities: ${authentication.authorities}" }
        createCompanyAccountService.execute(request)
        log.info { "Company account created successfully" }
    }
}