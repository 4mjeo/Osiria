package com.example.ohsiria.domain.manager.presentation

import com.example.ohsiria.domain.manager.presentation.dto.response.CompanyDetailResponse
import com.example.ohsiria.domain.manager.presentation.dto.response.CompanyListResponse
import com.example.ohsiria.domain.manager.presentation.dto.request.CreateCompanyAccountRequest
import com.example.ohsiria.domain.manager.service.CreateCompanyAccountService
import com.example.ohsiria.domain.manager.service.QueryCompanyDetailService
import com.example.ohsiria.domain.manager.service.QueryCompanyListService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/manager")
class ManagerController(
    private val createCompanyAccountService: CreateCompanyAccountService,
    private val queryCompanyDetailService: QueryCompanyDetailService,
    private val queryCompanyListService: QueryCompanyListService,
) {
    @PostMapping
    fun createCompanyAccount(@RequestBody request: CreateCompanyAccountRequest) {
        createCompanyAccountService.execute(request)
    }

    @GetMapping("/{company-id}")
    fun getCompanyDetails(@PathVariable("company-id") companyId: UUID): CompanyDetailResponse
        = queryCompanyDetailService.execute(companyId)

    @GetMapping
    fun getCompanyList(): List<CompanyListResponse>
        = queryCompanyListService.execute()
}
