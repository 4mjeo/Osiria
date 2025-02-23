package com.example.ohsiria.domain.room.service

import com.example.ohsiria.domain.room.entity.Room
import com.example.ohsiria.domain.room.presentation.dto.request.RegisterRoomRequest
import com.example.ohsiria.domain.room.presentation.dto.response.RegisterRoomResponse
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
    fun execute(request: RegisterRoomRequest): RegisterRoomResponse {
        val room = Room(
            number = request.number,
            introduction = request.introduction,
            guide = request.guide,
            amount = request.amount,
            caution = request.caution
        )

        request.attachments?.forEach { url -> room.addAttachment(url) }
        val savedRoom = roomRepository.save(room)

        request.serviceKeywords?.let { keywords ->
            val existingServices = serviceRepository.findByKeywords(keywords)
            val roomServices = existingServices.map { service ->
                RoomService(room = savedRoom, service = service)
            }

            roomServiceRepository.saveAll(roomServices)
        }

        return RegisterRoomResponse(roomId = savedRoom.id!!)
    }
}
