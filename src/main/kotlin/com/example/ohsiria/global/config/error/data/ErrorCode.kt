package com.example.ohsiria.global.config.error.data

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String,
) {

    //400
    ACCOUNT_ID_NOT_EXIST(HttpStatus.BAD_REQUEST, "존재하지 않는 계정입니다."),

    //401
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않는 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),

    //403
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "권한이 거부되었습니다."),

    //404
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "예약을 한 적이 없습니다."),

    //409
    ALREADY_RESERVED(HttpStatus.CONFLICT, "이미 예약했습니다."),
    ALREADY_CANCELED(HttpStatus.CONFLICT, "이미 취소했습니다."),

    //500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러"),
}
