package com.example.ohsiria.domain.room.service

import com.example.ohsiria.domain.room.exception.RoomNotFoundException
import com.example.ohsiria.domain.room.repository.RoomRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class DeleteRoomService(
    private val roomRepository: RoomRepository,
) {
    @Transactional
    fun execute(roomId: UUID) {
        val room = roomRepository.findByIdOrNull(roomId) ?: throw RoomNotFoundException

        roomRepository.delete(room)
    }
}
