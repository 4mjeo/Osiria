package com.example.ohsiria.domain.room.service

import com.example.ohsiria.domain.room.entity.Room
import com.example.ohsiria.domain.room.presentation.dto.RegisterRoomRequest
import com.example.ohsiria.domain.room.presentation.dto.RoomResponse
import com.example.ohsiria.domain.room.repository.RoomRepository
import com.example.ohsiria.domain.service.entity.RoomService
import com.example.ohsiria.domain.service.entity.Service
import com.example.ohsiria.domain.service.repository.RoomServiceRepository
import com.example.ohsiria.domain.service.repository.ServiceRepository
import org.springframework.transaction.annotation.Transactional

@org.springframework.stereotype.Service
class RegisterRoomService(
    private val roomRepository: RoomRepository,
    private val serviceRepository: ServiceRepository,
    private val roomServiceRepository: RoomServiceRepository
) {
    @Transactional
    fun execute(request: RegisterRoomRequest): RoomResponse {
        val room = Room(
            number = request.number,
            introduction = request.introduction,
            guide = request.guide
        )

        request.attachments?.forEach { url -> room.addAttachment(url) }
        val savedRoom = roomRepository.save(room)

        request.serviceKeywords?.let { keywords ->
            val existingServices = serviceRepository.findByKeywords(keywords)
                .associateBy { it.keyword }

            val roomServices = keywords.map { keyword ->
                val service = existingServices[keyword] ?: serviceRepository.save(Service(keyword = keyword))
                RoomService(room = savedRoom, service = service)
            }

            roomServiceRepository.saveAll(roomServices)
        }

        return RoomResponse(roomId = savedRoom.id!!)
    }
}
