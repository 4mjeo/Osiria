package com.example.ohsiria.infra.company

import com.example.ohsiria.domain.company.repository.CompanyRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Service
@Transactional
class CompanyResetScheduler(
    private val companyRepository: CompanyRepository
) {
    @Scheduled(cron = "0 0 0 * * ?")
    fun resetRemainingDays() {
        val today = LocalDate.now()
        companyRepository.findAll().forEach { company ->
            val yearsSinceCreation = ChronoUnit.YEARS.between(company.createdAt, today)
            if (yearsSinceCreation > 0 && today.dayOfYear == company.createdAt.dayOfYear) {
                company.resetRemainingDays()
                companyRepository.save(company)
            }
        }
    }
}

//@Scheduled(cron = "0 0 0 1 1 *")
//fun resetCompanyRemainingDays() {
//    val today = LocalDate.now()
//    companyRepository.findAll().forEach { company ->
//        company.resetRemainingDays()
//        companyRepository.save(company)
//    }
//}
