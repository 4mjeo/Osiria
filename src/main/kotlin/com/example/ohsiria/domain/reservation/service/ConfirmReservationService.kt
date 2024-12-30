package com.example.ohsiria.domain.reservation.service

import com.example.ohsiria.domain.reservation.entity.ReservationStatus
import com.example.ohsiria.domain.reservation.exception.ReservationNotFoundException
import com.example.ohsiria.domain.reservation.repository.ReservationRepository
import com.example.ohsiria.domain.user.entity.UserType
import com.example.ohsiria.global.common.facade.UserFacade
import com.example.ohsiria.global.config.error.exception.PermissionDeniedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ConfirmReservationService(
    private val reservationRepository: ReservationRepository,
    private val userFacade: UserFacade,
) {
    @Transactional
    fun execute(reservationId: UUID, newStatus: ReservationStatus) {
        val user = userFacade.getCurrentUser()
        val reservation = reservationRepository.findByIdOrNull(reservationId) ?: throw ReservationNotFoundException

        if (user.type != UserType.MANAGER) {
            throw PermissionDeniedException
        }

        when (newStatus) {
            ReservationStatus.RESERVED -> reservation.confirm()
            ReservationStatus.CANCELED -> reservation.cancel()
            ReservationStatus.WAITING -> throw IllegalArgumentException("대기 상태로 변경할 수 없습니다.")
        }

        reservationRepository.save(reservation)
    }
}
