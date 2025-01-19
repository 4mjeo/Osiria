package com.example.ohsiria.domain.room.presentation

import com.example.ohsiria.domain.room.presentation.dto.request.RegisterRoomRequest
import com.example.ohsiria.domain.room.presentation.dto.response.RegisterRoomResponse
import com.example.ohsiria.domain.room.presentation.dto.request.UpdateRoomRequest
import com.example.ohsiria.domain.room.presentation.dto.response.RoomListResponse
import com.example.ohsiria.domain.room.service.*
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
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
    fun queryRoomList(
        @RequestParam(required = false) startDate: LocalDate?,
        @RequestParam(required = false) endDate: LocalDate?
    ): List<RoomListResponse> = queryRoomListService.execute(startDate, endDate)

    @GetMapping("/{room-id}")
    fun queryRoomDetails(@PathVariable("room-id") roomId: UUID) =
        queryRoomDetailsService.execute(roomId)
}
