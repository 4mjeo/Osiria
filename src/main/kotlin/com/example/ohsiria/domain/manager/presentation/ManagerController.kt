package com.example.ohsiria.domain.manager.presentation

import com.example.ohsiria.domain.manager.presentation.dto.response.CompanyDetailResponse
import com.example.ohsiria.domain.manager.presentation.dto.response.CompanyListResponse
import com.example.ohsiria.domain.manager.presentation.dto.request.CreateCompanyAccountRequest
import com.example.ohsiria.domain.manager.presentation.dto.request.UpdateCompanyRequest
import com.example.ohsiria.domain.manager.presentation.dto.response.CreateCompanyResponse
import com.example.ohsiria.domain.manager.service.*
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/manager")
class ManagerController(
    private val createCompanyAccountService: CreateCompanyAccountService,
    private val queryCompanyDetailService: QueryCompanyDetailService,
    private val queryCompanyListService: QueryCompanyListService,
    private val updateCompanyService: UpdateCompanyService,
    private val deleteCompanyService: DeleteCompanyService
) {
    @PostMapping
    fun createCompanyAccount(@RequestBody request: CreateCompanyAccountRequest): CreateCompanyResponse =
        createCompanyAccountService.execute(request)

    @GetMapping("/{account-id}")
    fun getCompanyDetails(@PathVariable("account-id") accountId: String): CompanyDetailResponse =
        queryCompanyDetailService.execute(accountId)

    @GetMapping
    fun getCompanyList(): List<CompanyListResponse> = queryCompanyListService.execute()

    @PatchMapping("/{account-id}")
    fun updateCompanyAccount(
        @PathVariable("account-id") accountId: String,
        @RequestBody request: UpdateCompanyRequest
    ) {
        updateCompanyService.execute(accountId, request)
    }

    @DeleteMapping("/{account-id}")
    fun deleteCompanyAccount(@PathVariable("account-id") accountId: String) {
        deleteCompanyService.execute(accountId)
    }
}
