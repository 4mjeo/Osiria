package com.example.ohsiria.domain.room.presentation.dto.response

import java.util.UUID

data class RoomDetailsResponse(
    val roomNo: Long,
    val introduction: String,
    val guide: String,
    val amount: Long,
    val caution: String?,
    val attachments: List<String>?,
    val serviceKeywords: List<String>?
)

data class RoomListResponse(
    val roomId: UUID?,
    val roomNo: Long,
    val attachment: String?,
    val amount: Long,
)
