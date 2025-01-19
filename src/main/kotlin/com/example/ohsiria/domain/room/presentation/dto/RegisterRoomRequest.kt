package com.example.ohsiria.domain.room.presentation.dto

data class RegisterRoomRequest(
    val number: Long,
    val introduction: String,
    val guide: String,
    val attachments: List<String>?,
    val serviceKeywords: List<String>?
)
