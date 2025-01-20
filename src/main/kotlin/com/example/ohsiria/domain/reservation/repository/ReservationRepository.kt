package com.example.ohsiria.domain.reservation.repository

import com.example.ohsiria.domain.company.entity.Company
import com.example.ohsiria.domain.reservation.entity.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface ReservationRepository: JpaRepository<Reservation, UUID?> {
    fun findByCompany(company: Company): List<Reservation>

    fun findByStartDate(startDate: LocalDate): List<Reservation>
}
