package com.github.edumaxsantos.jlox;

public enum LoxStatus {
    RUNTIME_ERROR(70),
    ERROR(65),
    INVALID_PARAMS_ERROR(64),
    NO_ERROR(0);

    private final int errorCode;

    LoxStatus(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
