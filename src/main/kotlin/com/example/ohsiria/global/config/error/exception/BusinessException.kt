package com.example.ohsiria.global.config.error.exception

import com.example.ohsiria.global.config.error.data.ErrorCode

open class BusinessException(val errorCode: ErrorCode): RuntimeException(errorCode.message)
