package com.example.ohsiria.domain.manager.presentation.dto.response

import com.example.ohsiria.domain.company.entity.Company
import com.example.ohsiria.domain.reservation.entity.Reservation
import com.example.ohsiria.domain.reservation.presentation.dto.response.ReservationResponse

data class CompanyListResponse(
    val companyName: String,
    val accountId: String,
    val password: String,
    val remainWeekend: Int,
    val remainWeekday: Int
)

data class CompanyDetailResponse(
    val companyName: String,
    val reservations: List<ReservationResponse>
) {
    companion object {
        fun from(company: Company, reservations: List<Reservation>): CompanyDetailResponse {
            return CompanyDetailResponse(
                companyName = company.user.name,
                reservations = reservations.map {
                    ReservationResponse.from(it)
                }
            )
        }
    }
}
