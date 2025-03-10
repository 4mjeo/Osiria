package com.example.ohsiria.domain.company.presentation

import com.example.ohsiria.domain.company.presentation.dto.CompanyResponse
import com.example.ohsiria.domain.company.service.QueryRemainDaysService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/company")
class CompanyController(
    private val queryRemainDaysService: QueryRemainDaysService,
) {
    @GetMapping
    fun getRemainDays(): CompanyResponse =
        queryRemainDaysService.execute()
}
