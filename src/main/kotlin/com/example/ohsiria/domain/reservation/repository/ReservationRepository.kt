package com.example.ohsiria.domain.reservation.repository

import com.example.ohsiria.domain.reservation.entity.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface ReservationRepository: JpaRepository<Reservation, UUID?> {
    fun existsByStartDateAndEndDate(startDate: LocalDate, endDate: LocalDate): Boolean
}
