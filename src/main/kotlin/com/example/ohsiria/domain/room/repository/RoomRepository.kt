package com.example.ohsiria.domain.room.repository

import com.example.ohsiria.domain.room.entity.Room
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoomRepository: JpaRepository<Room, UUID?> {
    fun findByNumber(number: Long): Room?
}
