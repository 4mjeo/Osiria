package com.example.ohsiria.domain.reservation.exception

import com.example.ohsiria.global.config.error.data.ErrorCode
import com.example.ohsiria.global.config.error.exception.BusinessException

object InvalidCancellationException: BusinessException(ErrorCode.INVALID_CANCELLATION)
