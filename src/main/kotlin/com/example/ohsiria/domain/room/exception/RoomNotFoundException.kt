package com.example.ohsiria.domain.room.exception

import com.example.ohsiria.global.config.error.data.ErrorCode
import com.example.ohsiria.global.config.error.exception.BusinessException

object RoomNotFoundException: BusinessException(ErrorCode.ROOM_NOT_FOUND)
