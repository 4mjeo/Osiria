package com.example.ohsiria.domain.reservation.service

import com.example.ohsiria.domain.reservation.presentation.dto.ReserveRequest
import com.example.ohsiria.domain.reservation.repository.ReservationRepository
import com.example.ohsiria.domain.user.repository.UserRepository
import com.example.ohsiria.global.common.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReserveService(
    private val reservationRepository: ReservationRepository,
    private val userFacade: UserFacade,
    private val userRepository: UserRepository
) {
    @Transactional
    fun execute(request: ReserveRequest) {

    }
}