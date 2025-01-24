package com.example.ohsiria.domain.room.presentation.dto.request

data class UpdateRoomRequest(
    val introduction: String?,
    val guide: String?,
    val caution: String?,
    val attachments: List<String>?,
    val serviceKeywords: List<String>?
)
