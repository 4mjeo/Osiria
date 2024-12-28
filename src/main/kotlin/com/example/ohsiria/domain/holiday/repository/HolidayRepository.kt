package com.example.ohsiria.domain.holiday.repository

import com.example.ohsiria.domain.holiday.entity.Holiday
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface HolidayRepository : JpaRepository<Holiday, LocalDate> {
    fun findByDateBetween(startDate: LocalDate, endDate: LocalDate): List<Holiday>
}
