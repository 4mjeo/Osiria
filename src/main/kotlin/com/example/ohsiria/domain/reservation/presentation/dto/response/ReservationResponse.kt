package com.example.ohsiria.domain.reservation.presentation.dto.response

import com.example.ohsiria.domain.reservation.entity.Reservation
import com.example.ohsiria.domain.room.entity.RoomType
import java.time.LocalDate
import java.util.*

data class ReservationResponse(
    val id: UUID,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val name: String,
    val roomType: RoomType,
    val isCancel: Boolean,
) {
    companion object {
        fun from(reservation: Reservation): ReservationResponse =
            ReservationResponse(
                id = reservation.id!!,
                startDate = reservation.startDate,
                endDate = reservation.endDate,
                name = reservation.name,
                roomType = reservation.roomType,
                isCancel = reservation.isCancel,
            )
    }
}
