package com.example.ohsiria.domain.company.exception

import com.example.ohsiria.global.config.error.data.ErrorCode
import com.example.ohsiria.global.config.error.exception.BusinessException

object CompanyNotFoundException: BusinessException(ErrorCode.COMPANY_NOT_FOUND)
