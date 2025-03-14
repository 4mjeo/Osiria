package com.example.ohsiria.global.config.error.data

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String,
) {

    //400
    ACCOUNT_ID_NOT_EXIST(HttpStatus.BAD_REQUEST, "존재하지 않는 계정입니다."),
    PASSWORD_MISMATCHED(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    COMPANY_MISMATCH(HttpStatus.BAD_REQUEST, "회사가 일치하지 않습니다."),
    INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST, "유효하지 않은 기간입니다."),
    SHORTAGE_REMAIN_DAYS(HttpStatus.BAD_REQUEST, "잔여기간이 부족합니다."),
    INVALID_CANCELLATION(HttpStatus.BAD_REQUEST, "예약 3일 전부터는 취소할 수 없습니다."),
    INVALID_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 확장자입니다."),

    //401
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않는 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),

    //403
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "권한이 거부되었습니다."),

    //404
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "예약을 한 적이 없습니다."),
    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "회사를 찾을 없습니다."),
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "방을 찾을 수 없습니다."),

    //409
    ALREADY_RESERVED(HttpStatus.CONFLICT, "이미 예약했습니다."),
    ALREADY_CANCELED(HttpStatus.CONFLICT, "이미 취소했습니다."),
    ALREADY_EXISTING_ACCOUNT(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    RESERVATION_CONFLICT(HttpStatus.CONFLICT, "이미 예약된 날짜입니다."),
    ALREADY_PAID(HttpStatus.CONFLICT, "이미 결제했습니다."),

    //500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러"),
}
