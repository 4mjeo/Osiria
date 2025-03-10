package com.example.ohsiria.domain.reservation.exception

import com.example.ohsiria.global.config.error.data.ErrorCode
import com.example.ohsiria.global.config.error.exception.BusinessException

object ShortageRemainDaysException: BusinessException(ErrorCode.SHORTAGE_REMAIN_DAYS)
