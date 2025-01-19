package com.example.ohsiria.domain.room.presentation

import com.example.ohsiria.domain.room.presentation.dto.request.RegisterRoomRequest
import com.example.ohsiria.domain.room.presentation.dto.response.RegisterRoomResponse
import com.example.ohsiria.domain.room.presentation.dto.request.UpdateRoomRequest
import com.example.ohsiria.domain.room.presentation.dto.response.RoomListResponse
import com.example.ohsiria.domain.room.service.*
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
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
    private val updateRoomService: UpdateRoomService,
    private val queryRoomListService: QueryRoomListService,
    private val queryRoomDetailsService: QueryRoomDetailsService,
) {
    @PostMapping
    fun registerRoom(@RequestBody @Valid request: RegisterRoomRequest): RegisterRoomResponse =
        registerRoomService.execute(request)

    @DeleteMapping("/{room-id}")
    fun deleteRoom(@PathVariable("room-id") roomId: UUID) {
        deleteRoomService.execute(roomId)
    }

    @PatchMapping("/{room-id}")
    fun updateRoom(
        @PathVariable("room-id") roomId: UUID,
        @RequestBody @Valid request: UpdateRoomRequest
    ) {
        updateRoomService.execute(roomId, request)
    }

    @GetMapping
    fun queryRoomList(): List<RoomListResponse> =
        queryRoomListService.execute()

    @GetMapping("/{room-id}")
    fun queryRoomDetails(@PathVariable("room-id") roomId: UUID) =
        queryRoomDetailsService.execute(roomId)
}
