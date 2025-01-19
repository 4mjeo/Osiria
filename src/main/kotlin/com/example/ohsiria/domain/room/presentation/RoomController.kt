package com.example.ohsiria.domain.room.presentation

import com.example.ohsiria.domain.room.presentation.dto.RegisterRoomRequest
import com.example.ohsiria.domain.room.presentation.dto.RoomResponse
import com.example.ohsiria.domain.room.service.DeleteRoomService
import com.example.ohsiria.domain.room.service.RegisterRoomService
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/room")
@Validated
class RoomController(
    private val registerRoomService: RegisterRoomService,
    private val deleteRoomService: DeleteRoomService,
) {
    @PostMapping
    fun registerRoom(@RequestBody @Valid request: RegisterRoomRequest): RoomResponse =
        registerRoomService.execute(request)

    @DeleteMapping("/{room-id}")
    fun deleteRoom(@PathVariable("room-id") roomId: UUID) {
        deleteRoomService.execute(roomId)
    }
}
