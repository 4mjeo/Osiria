package com.example.ohsiria.domain.reservation.presentation

import com.example.ohsiria.domain.reservation.entity.ReservationStatus
import com.example.ohsiria.domain.reservation.presentation.dto.request.ReserveRequest
import com.example.ohsiria.domain.reservation.presentation.dto.response.ManagerReservationResponse
import com.example.ohsiria.domain.reservation.presentation.dto.response.ReservationResponse
import com.example.ohsiria.domain.reservation.presentation.dto.response.ReserveResponse
import com.example.ohsiria.domain.reservation.service.*
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.UUID

@RestController
@RequestMapping("/reservation")
@Validated
class ReservationController(
    private val reserveService: ReserveService,
    private val cancelReservationService: CancelReservationService,
    private val queryMyReservationService: QueryMyReservationService,
    private val queryAllReservationsService: QueryAllReservationsService,
    private val confirmReservationService: ConfirmReservationService,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun reserve(@RequestBody @Valid request: ReserveRequest): ReserveResponse =
        reserveService.execute(request)

    @PatchMapping("/{reservation-id}")
    fun cancel(@PathVariable("reservation-id") reservationId: UUID) {
        cancelReservationService.execute(reservationId)
    }

    @GetMapping("/company")
    fun queryMyReservation(
        @RequestParam(defaultValue = "false") isHistory: Boolean
    ): List<ReservationResponse> = queryMyReservationService.execute(isHistory)

    @GetMapping("/manager")
    fun queryAllReservation(@RequestParam date: LocalDate): List<ManagerReservationResponse> =
        queryAllReservationsService.execute(date)

    @PatchMapping("/confirm/{reservation-id}")
    fun confirmReservation(
        @PathVariable("reservation-id") reservationId: UUID,
        @RequestParam newStatus: ReservationStatus
    ) {
        confirmReservationService.execute(reservationId, newStatus)
    }
}
