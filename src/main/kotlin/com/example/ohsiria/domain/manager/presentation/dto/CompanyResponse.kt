package com.example.ohsiria.domain.manager.presentation.dto

import com.example.ohsiria.domain.company.entity.Company
import com.example.ohsiria.domain.reservation.entity.Reservation
import com.example.ohsiria.domain.reservation.presentation.dto.response.ReservationResponse

data class CompanyListResponse(
    val companyName: String,
)

data class CompanyDetailResponse(
    val companyName: String,
    val remainingWeekdays: Int,
    val remainingWeekends: Int,
    val reservations: List<ReservationResponse>
) {
    companion object {
        fun from(company: Company, reservations: List<Reservation>): CompanyDetailResponse {
            return CompanyDetailResponse(
                companyName = company.user.name,
                remainingWeekdays = company.remainWeekday,
                remainingWeekends = company.remainWeekend,
                reservations = reservations.map {
                    ReservationResponse.from(it)
                }
            )
        }
    }
}
