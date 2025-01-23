package com.example.ohsiria.domain.room.presentation.dto.response

data class RoomDetailsResponse(
    val roomNo: Long,
    val introduction: String,
    val guide: String,
    val amount: Long,
    val attachments: List<String>?,
    val serviceKeywords: List<String>?
)

data class RoomListResponse(
    val roomNo: Long,
    val attachment: String?
)
