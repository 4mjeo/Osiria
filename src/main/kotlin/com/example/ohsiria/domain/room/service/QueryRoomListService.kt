package com.example.ohsiria.domain.room.service

import com.example.ohsiria.domain.reservation.repository.ReservationRepositoryCustom
import com.example.ohsiria.domain.room.presentation.dto.response.RoomListResponse
import com.example.ohsiria.domain.room.repository.RoomRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class QueryRoomListService(
    private val roomRepository: RoomRepository,
    private val reservationRepositoryCustom: ReservationRepositoryCustom,
) {
    @Transactional(readOnly = true)
    fun execute(startDate: LocalDate?, endDate: LocalDate?): List<RoomListResponse> {
        val availableRooms = if (startDate != null && endDate != null) {
            reservationRepositoryCustom.findAvailableRooms(startDate, endDate)
        } else {
            roomRepository.findAll()
        }

        return availableRooms.map { room ->
            RoomListResponse(
                roomId = room.id,
                roomNo = room.number,
                attachment = room.attachments.firstOrNull()?.imageUrl
            )
        }
    }
}
