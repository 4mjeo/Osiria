package com.example.ohsiria.domain.auth.exception

import com.example.ohsiria.global.config.error.data.ErrorCode
import com.example.ohsiria.global.config.error.exception.BusinessException

object PasswordMismatchedException: BusinessException(ErrorCode.PASSWORD_MISMATCHED)
