package com.example.ohsiria.domain.reservation.service

import com.example.ohsiria.domain.reservation.presentation.dto.response.ManagerReservationResponse
import com.example.ohsiria.domain.reservation.repository.ReservationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class QueryAllReservationsService(
    private val reservationRepository: ReservationRepository
) {
    @Transactional(readOnly = true)
    fun execute(date: LocalDate? = null): List<ManagerReservationResponse> {
        return reservationRepository.findAll()
            .filter { reservation ->
                date?.let { d ->
                    (reservation.startDate <= d && d <= reservation.endDate)
                } ?: true
            }
            .map { ManagerReservationResponse.from(it) }
    }
}
