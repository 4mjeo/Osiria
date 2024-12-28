package com.example.ohsiria.infra.holiday

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class GetHolidayScheduler(
    private val getHolidayService: GetHolidayService
) {
    @Transactional
    @Scheduled(cron = "0 0 1 1 * ?") // 매년 1월 1일
    fun updateHolidaysNextYear() {
        val nextYear = LocalDate.now().year + 1
        getHolidayService.updateHolidays(nextYear)
    }

    @Transactional
    @Scheduled(cron = "0 0 1 * * ?") // 매원 1일
    fun updateHolidaysCurrentYear() {
        val currentYear = LocalDate.now().year
        getHolidayService.updateHolidays(currentYear)
    }
}
