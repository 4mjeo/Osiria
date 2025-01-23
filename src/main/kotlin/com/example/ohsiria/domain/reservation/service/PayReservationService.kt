package com.example.ohsiria.domain.reservation.service

import com.example.ohsiria.domain.reservation.exception.AlreadyPaidException
import com.example.ohsiria.domain.reservation.exception.ReservationNotFoundException
import com.example.ohsiria.domain.reservation.repository.ReservationRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class PayReservationService(
    private val reservationRepository: ReservationRepository
) {
    @Transactional
    fun execute(reservationId: UUID) {
        val reservation = reservationRepository.findByIdOrNull(reservationId) ?: throw ReservationNotFoundException

        if (reservation.paidAt != null) {
            throw AlreadyPaidException
        }

        reservation.markAsPaid()
        reservationRepository.save(reservation)
    }
}
