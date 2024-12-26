package com.example.ohsiria.domain.company.exception

import com.example.ohsiria.global.config.error.data.ErrorCode
import com.example.ohsiria.global.config.error.exception.BusinessException

object InvalidDateRangeException: BusinessException(ErrorCode.INVALID_DATE_RANGE)
