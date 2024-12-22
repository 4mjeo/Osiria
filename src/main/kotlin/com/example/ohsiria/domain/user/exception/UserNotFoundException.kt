package com.example.ohsiria.domain.user.exception

import com.example.ohsiria.global.config.error.data.ErrorCode
import com.example.ohsiria.global.config.error.exception.BusinessException

object UserNotFoundException : BusinessException(ErrorCode.USER_NOT_FOUND)
