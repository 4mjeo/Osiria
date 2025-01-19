package com.example.ohsiria.domain.service.repository

import com.example.ohsiria.domain.room.entity.Room
import com.example.ohsiria.domain.service.entity.RoomService
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoomServiceRepository: JpaRepository<RoomService, UUID?> {
    fun deleteByRoom(room: Room)
}
