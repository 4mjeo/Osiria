package com.example.ohsiria.domain.reservation.presentation

import com.example.ohsiria.domain.reservation.presentation.dto.ReserveRequest
import com.example.ohsiria.domain.reservation.service.CancelReservationService
import com.example.ohsiria.domain.reservation.service.ReserveService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/reservation")
@Validated
class ReservationController(
    private val reserveService: ReserveService,
    private val cancelReservationService: CancelReservationService,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun reserve(@RequestBody @Valid request: ReserveRequest) {
        reserveService.execute(request)
    }

    @PatchMapping("/{reservation-id}")
    fun cancel(@PathVariable("reservation-id") reservationId: UUID) {
        cancelReservationService.execute(reservationId)
    }
}
