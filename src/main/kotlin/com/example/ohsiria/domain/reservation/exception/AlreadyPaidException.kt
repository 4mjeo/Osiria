package com.example.ohsiria.domain.reservation.exception

import com.example.ohsiria.global.config.error.data.ErrorCode
import com.example.ohsiria.global.config.error.exception.BusinessException

object AlreadyPaidException: BusinessException(ErrorCode.ALREADY_PAID)
