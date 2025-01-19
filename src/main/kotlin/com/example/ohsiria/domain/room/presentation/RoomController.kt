package com.example.ohsiria.domain.room.presentation

import com.example.ohsiria.domain.room.presentation.dto.RegisterRoomRequest
import com.example.ohsiria.domain.room.presentation.dto.RoomResponse
import com.example.ohsiria.domain.room.service.RegisterRoomService
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/room")
@Validated
class RoomController(
    private val registerRoomService: RegisterRoomService,
) {
    @PostMapping("/register")
    fun register(@RequestBody @Valid request: RegisterRoomRequest): RoomResponse =
        registerRoomService.execute(request)
}
