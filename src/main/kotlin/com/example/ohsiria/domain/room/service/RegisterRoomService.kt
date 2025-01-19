package com.example.ohsiria.domain.room.service

import com.example.ohsiria.domain.room.entity.Room
import com.example.ohsiria.domain.room.presentation.dto.RegisterRoomRequest
import com.example.ohsiria.domain.room.presentation.dto.RoomResponse
import com.example.ohsiria.domain.room.repository.RoomRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterRoomService(
    private val roomRepository: RoomRepository,
) {
    @Transactional
    fun execute(request: RegisterRoomRequest): RoomResponse {
        val room = Room(
            number = request.number,
            introduction = request.introduction,
            guide = request.guide
        )
        request.attachments?.forEach { url ->
            room.addAttachment(url)
        }
        roomRepository.save(room)

        return RoomResponse(roomId = room.id!!)
    }
}
