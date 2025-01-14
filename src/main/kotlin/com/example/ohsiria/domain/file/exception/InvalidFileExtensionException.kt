package com.example.ohsiria.domain.file.exception

import com.example.ohsiria.global.config.error.data.ErrorCode
import com.example.ohsiria.global.config.error.exception.BusinessException

object InvalidFileExtensionException: BusinessException(ErrorCode.INVALID_FILE_EXTENSION)
