package com.lq.learn2026.exception;

public class ESException extends Exception {
    private int code;

    public ESException(String message) {
        super(message);
    }

    public ESException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ESException(ESErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
}
