package com.example.ohsiria.domain.room.presentation.dto.request

data class RegisterRoomRequest(
    val number: Long,
    val introduction: String,
    val guide: String,
    val amount: Long,
    val attachments: List<String>?,
    val serviceKeywords: List<String>?
)
