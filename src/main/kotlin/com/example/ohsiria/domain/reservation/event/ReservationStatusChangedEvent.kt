package com.example.ohsiria.domain.reservation.event

import com.example.ohsiria.domain.reservation.entity.ReservationStatus
import java.util.UUID

data class ReservationStatusChangedEvent(
    val reservationId: UUID,
    val newStatus: ReservationStatus,
    val phoneNumber: String
)
