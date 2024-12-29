package com.example.ohsiria.domain.reservation.service

import com.example.ohsiria.domain.reservation.presentation.dto.response.ManagerReservationResponse
import com.example.ohsiria.domain.reservation.repository.ReservationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryAllReservationsService(
    private val reservationRepository: ReservationRepository
) {
    @Transactional(readOnly = true)
    fun execute(): List<ManagerReservationResponse> {
        return reservationRepository.findAll()
            .map { ManagerReservationResponse.from(it) }
    }
}
