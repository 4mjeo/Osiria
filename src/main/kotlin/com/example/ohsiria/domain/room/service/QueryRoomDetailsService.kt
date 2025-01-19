package com.example.ohsiria.domain.room.service

import com.example.ohsiria.domain.room.exception.RoomNotFoundException
import com.example.ohsiria.domain.room.presentation.dto.response.RoomDetailsResponse
import com.example.ohsiria.domain.room.repository.RoomRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class QueryRoomDetailsService(
    private val roomRepository: RoomRepository,
) {
    @Transactional(readOnly = true)
    fun execute(roomId: UUID): RoomDetailsResponse {
        val room = roomRepository.findByIdOrNull(roomId) ?: throw RoomNotFoundException

        return RoomDetailsResponse(
            roomNo = room.number,
            introduction = room.introduction,
            guide = room.guide,
            attachments = room.attachments.map { it.imageUrl },
            serviceKeywords = room.roomServices.map { it.service.keyword }
        )
    }
}
