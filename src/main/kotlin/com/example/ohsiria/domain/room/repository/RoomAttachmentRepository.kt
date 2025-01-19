package com.example.ohsiria.domain.room.repository

import com.example.ohsiria.domain.room.entity.RoomAttachment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface RoomAttachmentRepository: JpaRepository<RoomAttachment, UUID?>
