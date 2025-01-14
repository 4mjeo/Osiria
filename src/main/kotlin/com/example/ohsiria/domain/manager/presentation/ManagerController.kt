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

    @GetMapping("/{company-id}")
    fun getCompanyDetails(@PathVariable("company-id") companyId: UUID): CompanyDetailResponse =
        queryCompanyDetailService.execute(companyId)

    @GetMapping
    fun getCompanyList(): List<CompanyListResponse> = queryCompanyListService.execute()

    @PatchMapping("/{company-id}")
    fun updateCompanyAccount(
        @PathVariable("company-id") companyId: UUID,
        @RequestBody request: UpdateCompanyRequest
    ) {
        updateCompanyService.execute(companyId, request)
    }

    @DeleteMapping("/{company-id}")
    fun deleteCompanyAccount(@PathVariable("company-id") companyId: UUID) {
        deleteCompanyService.execute(companyId)
    }
}
