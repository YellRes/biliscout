package com.yellres.biliscout.exception;

public class TooManyRequestException extends RuntimeException {

    public TooManyRequestException(int times) {
        super("fail after " + times + " try");
    }
}
