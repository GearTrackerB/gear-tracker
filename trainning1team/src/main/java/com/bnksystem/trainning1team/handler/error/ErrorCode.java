package com.bnksystem.trainning1team.handler.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500, "서버 에러입니다"),
    LOGIN_ID_NOTFOUND(400, "로그인 오류입니다."),

    BAD_REQUEST(400, "잘못된 요청입니다.");

    private final int status;
    private final String message;
}
