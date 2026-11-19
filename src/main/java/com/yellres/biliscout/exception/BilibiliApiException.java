package com.yellres.biliscout.exception;

public class BilibiliApiException extends RuntimeException {

    public BilibiliApiException(int code, String message) {
        super("B站接口返回：" + code + ": " + message);
    }
}
