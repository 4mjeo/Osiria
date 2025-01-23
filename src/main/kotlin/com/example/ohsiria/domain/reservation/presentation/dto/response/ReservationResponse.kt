package com.example.ohsiria.domain.reservation.presentation.dto.response

import com.example.ohsiria.domain.reservation.entity.Reservation
import com.example.ohsiria.domain.reservation.entity.ReservationStatus
import java.time.LocalDate
import java.util.*

data class ReservationResponse(
    val id: UUID,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val name: String,
    val roomNo: Long,
    val status: ReservationStatus,
    val amount: Long,
) {
    companion object {
        fun from(reservation: Reservation): ReservationResponse =
            ReservationResponse(
                id = reservation.id!!,
                startDate = reservation.startDate,
                endDate = reservation.endDate,
                name = reservation.name,
                roomNo = reservation.room.number,
                status = reservation.status,
                amount = reservation.room.amount,
            )
    }
}

data class ManagerReservationResponse(
    val id: UUID,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val name: String,
    val phoneNumber: String,
    val accountNumber: String,
    val roomNo: Long,
    val status: ReservationStatus,
    val companyName: String,
    val amount: Long,
) {
    companion object {
        fun from(reservation: Reservation): ManagerReservationResponse {
            return ManagerReservationResponse(
                id = reservation.id!!,
                startDate = reservation.startDate,
                endDate = reservation.endDate,
                name = reservation.name,
                phoneNumber = reservation.phoneNumber,
                accountNumber = reservation.accountNumber,
                roomNo = reservation.room.number,
                status = reservation.status,
                companyName = reservation.company.user.name,
                amount = reservation.room.amount,
            )
        }
    }
}
