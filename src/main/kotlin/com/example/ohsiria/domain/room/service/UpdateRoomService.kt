package com.example.ohsiria.domain.room.service

import com.example.ohsiria.domain.room.exception.RoomNotFoundException
import com.example.ohsiria.domain.room.presentation.dto.UpdateRoomRequest
import com.example.ohsiria.domain.room.repository.RoomRepository
import com.example.ohsiria.domain.service.entity.RoomService
import com.example.ohsiria.domain.service.entity.Service
import com.example.ohsiria.domain.service.repository.RoomServiceRepository
import com.example.ohsiria.domain.service.repository.ServiceRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import java.util.*

@org.springframework.stereotype.Service
class UpdateRoomService(
    private val roomRepository: RoomRepository,
    private val serviceRepository: ServiceRepository,
    private val roomServiceRepository: RoomServiceRepository
) {
    @Transactional
    fun execute(roomId: UUID, request: UpdateRoomRequest) {
        val room = roomRepository.findByIdOrNull(roomId) ?: throw RoomNotFoundException

        room.update(request.introduction, request.guide)
        request.attachments?.let { newAttachments ->
            room.clearAttachments()
            newAttachments.forEach { url -> room.addAttachment(url) }
        }

        request.serviceKeywords?.let { keywords ->
            roomServiceRepository.deleteByRoom(room)

            val existingServices = serviceRepository.findByKeywords(keywords)
                .associateBy { it.keyword }

            val roomServices = keywords.map { keyword ->
                val service = existingServices[keyword] ?: serviceRepository.save(Service(keyword = keyword))
                RoomService(room = room, service = service)
            }
            roomServiceRepository.saveAll(roomServices)
        }
        roomRepository.save(room)
    }
}
