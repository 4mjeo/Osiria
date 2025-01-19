package com.example.ohsiria.domain.room.service

import com.example.ohsiria.domain.room.presentation.dto.response.RoomListResponse
import com.example.ohsiria.domain.room.repository.RoomRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryRoomListService(
    private val roomRepository: RoomRepository,
) {
    @Transactional(readOnly = true)
    fun execute(): List<RoomListResponse> {
        return roomRepository.findAll().map { room ->
            RoomListResponse(
                roomNo = room.number,
                attachment = room.attachments.firstOrNull()?.imageUrl
            )
        }
    }
}
